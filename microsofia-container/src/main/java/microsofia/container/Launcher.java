package microsofia.container;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import com.google.inject.Guice;
import com.google.inject.Injector;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.application.IApplication;
import microsofia.container.module.IModule;

public class Launcher {
	private static Log log=LogFactory.getLog(Launcher.class);
	private String[] arguments;
	private List<IModule> modules;
	private ApplicationConfig applicationConfig;
	private List<IApplication> applications;
	private IApplication currentApplication;
 	
	public Launcher(){
		modules=new ArrayList<>();
		applications=new ArrayList<IApplication>();
	}

	public List<IModule> getModules(){
		return modules;
	}
	
	public IApplication getCurrentApplication(){
		return currentApplication;
	}
	
	public List<IApplication> getApplications(){
		return applications;
	}
	
	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	private void preInitModules(LauncherContext context){
		ServiceLoader<IModule> moduleLoader=ServiceLoader.load(IModule.class,Launcher.class.getClassLoader());
		moduleLoader.forEach(modules::add);
		context.setContainerModules(modules);
		
		modules.stream().forEach(it->{
			it.preInit(context);
		});
	}
	
	private void injectModuleMembers(Injector injector){
		modules.stream().forEach(it->{
			injector.injectMembers(it);
		});
	}
	
	private void postInitModules(LauncherContext context){
		modules.stream().forEach(it->{
			it.postInit(context);
		});
	}
	
	private IApplication loadApplication(LauncherContext context){
		ServiceLoader<IApplication> moduleLoader=ServiceLoader.load(IApplication.class,Launcher.class.getClassLoader());
		moduleLoader.forEach(applications::add);
		
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
		context.setCurrentApplication(currentApplication);
		currentApplication.preInit(context);
		return currentApplication;
	}
	
	private void postInitApplication(LauncherContext context,IApplication application){
		context.getInjector().injectMembers(application);
		application.postInit(context);
	}
	
	public IApplication start() throws Throwable{
		LauncherContext context=new LauncherContext(arguments);
		context.setCurrentApplicationConfig(getApplicationConfig());

		log.info("Loading modules...");
		preInitModules(context);
		
		log.info("Loading application(s)...");
		currentApplication=loadApplication(context);
	
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
		return currentApplication;
	}

	public void stop(){
		modules.forEach(IModule::stop);
		currentApplication.stop();
	}
	
	public static void main(String[] argv, Element[] element) throws Throwable{
		Launcher launcher=new Launcher();
		launcher.setArguments(argv);
		if (element!=null){
			ApplicationConfig[] apps=ApplicationConfig.readFrom(element);
			if (apps==null || apps.length==0){
				throw new IllegalStateException("No application is configured to start. Please check your settings file.");
			}
			launcher.setApplicationConfig(apps[0]);//TODO for the moment we just launch one
		}
		launcher.start();
	}
}
