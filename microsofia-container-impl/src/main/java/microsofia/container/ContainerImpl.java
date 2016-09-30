package microsofia.container;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.application.IApplication;
import microsofia.container.module.IModule;

/**
 * Entry point of microsofia-container.<br>
 * 
 * The launcher will first load all the container modules and the application to run, then iterate on them in 2 steps.<br>
 * In the first step of pre-initialization, Guice modules are created and added in the LauncherContext.<br>
 * Once the step of pre-initialization is done, a Guice injector is created with all the created Guice modules, 
 * then a second iteration of post-initialization is done.
 * 
 * */
public class ContainerImpl extends Container{
	private static Log log=LogFactory.getLog(ContainerImpl.class);
	private List<IModule> modules;
	private List<IApplication> applications;
	private IApplication currentApplication;
 	
	public ContainerImpl(){
		modules=new ArrayList<>();
		applications=new ArrayList<IApplication>();
	}

	/**
	 * List of all loaded container's modules.
	 * 
	 * @return the container's modules
	 * */
	public List<IModule> getModules(){
		return modules;
	}
	
	/**
	 * Return the current application that was loaded and run.
	 * 
	 * @return the application that is run
	 * */
	public IApplication getCurrentApplication(){
		return currentApplication;
	}
	
	/**
	 * Return the list of all available applications
	 * 
	 * @return the list of all available applications
	 * */
	public List<IApplication> getApplications(){
		return applications;
	}

	/*
	 * First iteration of the modules in order to pre-initialize the launcher context
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
	 * as all available configuration (the application configuration and the arguments).
	 * 
	 * @return the started application
	 * */
	public void start() throws Throwable{
		//creating the context that will be used for startup
		InitializationContext context=new InitializationContext(arguments);
		//setting the application configuration to be used
		context.setApplicationConfig(getApplicationConfig());

		log.info("Loading modules...");
		preInitModules(context);
		
		log.info("Loading application(s)...");
		currentApplication=loadApplication(context);
	
		//create Guice injector
		Injector injector=Guice.createInjector(context.getGuiceModules());
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
	 * Stops the container. This will method will stop any running thread, socket server, ... created by the container and 
	 * frees any resource handled by the container.
	 * 
	 * */
	public void stop(){
		modules.forEach(IModule::stop);
		currentApplication.stop();
	}
}
