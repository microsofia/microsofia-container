package microsofia.container;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import microsofia.container.application.ApplicationProvider;
import microsofia.container.application.IApplication;
import microsofia.container.module.IModule;

/**
 * The implementation of a microsofia container.<br>
 * 
 * */
public class ContainerImpl extends Container{
	private static Log log=LogFactory.getLog(ContainerImpl.class);
	//the container modules
	private List<IModule> modules;
	//all available applications
	private List<IApplication> applications;
	//the application instance to run
	private IApplication currentApplication;
	//Guice injector
	private Injector injector;
	private List<ApplicationProvider> applicationProviders;
 	
	public ContainerImpl(){
		modules=new ArrayList<>();
		applications=new ArrayList<IApplication>();
		applicationProviders=new ArrayList<>();
	}

	/**
	 * Lists of all loaded container's modules.
	 * 
	 * @return the container's modules
	 * */
	public List<IModule> getModules(){
		return modules;
	}
	
	/**
	 * Returns the current application that was loaded and run.
	 * 
	 * @return the application that is run
	 * */
	public IApplication getCurrentApplication(){
		return currentApplication;
	}
	
	/**
	 * Returns the list of all available applications
	 * 
	 * @return the list of all available applications
	 * */
	public List<IApplication> getApplications(){
		return applications;
	}

	public void addApplicationProvider(ApplicationProvider provider){
		applicationProviders.add(provider);
	}
	
	/*
	 * Adds native container Guice module.
	 * */
	private void addNativeModules(InitializationContext context){
		context.addGuiceModule(new AbstractModule() {
			
			@Override
			protected void configure() {
				bind(Container.class).toInstance(ContainerImpl.this);

				//listen to Guice module for PostConstruct call
				bindListener( Matchers.any() , new TypeListener() {//TODO document PostConstruct usage		
				    @Override
				    public <I> void hear(final TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
				        typeEncounter.register(new InjectionListener<I>() {
				            @Override
				            public void afterInjection(Object i) {
				            	try{
					            	Method method=ClassUtils.getMethod(i, PostConstruct.class);
					                if (method!=null){
					                	method.invoke(i);
					                }
				            	}catch(Exception e){
				            		throw new ContainerException(e.getMessage(), e);
				            	}
				            }
				        });
				    }
				});
			}
		});
	}
	
	/*
	 * First iteration of the modules in order to pre-initialize the modules
	 */
	private void preInitModules(InitializationContext context){
		ServiceLoader<IModule> moduleLoader=ServiceLoader.load(IModule.class,ContainerImpl.class.getClassLoader());
		moduleLoader.forEach(modules::add);
		context.setContainerModules(modules);
		
		modules.stream().forEach(it->{
			it.preInit(context);
		});
	}
	
	/*
	 * After the creation of Guice injector, we try to inject all needed fields of modules
	 * */
	private void injectModuleMembers(Injector injector){
		modules.stream().forEach(it->{
			injector.injectMembers(it);
		});
	}
	
	/*
	 * Second iteration of the modules in order to post-initialize the modules
	 * */
	private void postInitModules(InitializationContext context){
		modules.stream().forEach(it->{
			it.postInit(context);
		});
	}
	
	/*
	 * Loading the application from the available applications.
	 * */
	private IApplication loadApplication(InitializationContext context){
		//loading all available applications
		ServiceLoader<IApplication> moduleLoader=ServiceLoader.load(IApplication.class,ContainerImpl.class.getClassLoader());
		moduleLoader.forEach(applications::add);
		
		//loading all available applications from providers
		ServiceLoader<ApplicationProvider> providerLoaders=ServiceLoader.load(ApplicationProvider.class,ContainerImpl.class.getClassLoader());
		providerLoaders.forEach(it->{
			it.getApplication(context).forEach(applications::add);
		});
		
		applicationProviders.forEach(it->{
			it.getApplication(context).forEach(applications::add);
		});
		
		//which available one has a type that we need
		currentApplication=null;
		for (IApplication a : applications){//should be put in a map if nb increases...
			if (a.getDescriptor().getType().equals(applicationConfig.getType())){
				currentApplication=a;
				break;
			}
		}
		if (currentApplication==null){
			throw new IllegalStateException("No application with type "+applicationConfig.getType()+" is found.");
		}
		//we set the found one in the context
		context.setCurrentApplication(currentApplication);
		
		//pre-init of the application
		currentApplication.preInit(context);
		return currentApplication;
	}
	
	/*
	 * Like for the modules, we inject any members and post-init the application.
	 * */
	private void postInitApplication(InitializationContext context,IApplication application){
		context.getInjector().injectMembers(application);
		application.postInit(context);
	}
	
	/**
	 * Method that will trigger the container to load its modules and to start the application using
	 * the available configuration (the application configuration and the arguments).
	 * 
	 * @return the started application
	 * */
	public void start() throws Throwable{
		//creating the context that will be used for startup
		InitializationContext context=new InitializationContext(arguments);
		//setting the application configuration to be used
		context.setApplicationConfig(getApplicationConfig());
		
		//add native Guice modules
		addNativeModules(context);

		log.info("Loading modules...");
		preInitModules(context);
		
		log.info("Loading application(s)...");
		currentApplication=loadApplication(context);
	
		//create Guice injector
		injector=Guice.createInjector(context.getGuiceModules());
		context.setInjector(injector);
		
		log.info("Injecting in modules...");
		injectModuleMembers(injector);
		
		//all modules should be initiated
		log.info("Initializing modules...");
		postInitModules(context);
		
		//not all applications initiated, only configured ones
		log.info("Initializing application...");
		postInitApplication(context,currentApplication);

		log.info("Running the application...");
		currentApplication.run();
	}
	
	/**
	 * Injects dependencies into the fields and methods of the object.
	 * 
	 * */
	@Override
	public void injectMembers(Object object){
		injector.injectMembers(object);
	}
	
	/**
	 * Returns an instance of type c with dependencies injected.
	 * 
	 * */
	@Override
	public <T> T getInstance(Class<T> c){
		return injector.getInstance(c);
	}

	/**
	 * Stops the container. This will method will stop any running thread, socket server, ... created by the container and 
	 * frees any resource handled by the container.
	 * 
	 * */
	public void stop(){
		modules.forEach(IModule::stop);
		currentApplication.stop();
	}
}
