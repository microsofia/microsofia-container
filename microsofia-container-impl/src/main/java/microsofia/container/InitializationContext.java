package microsofia.container;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Module;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.IApplication;
import microsofia.container.module.IModule;

/**
 * A convenient context that is used while creating and initializing the container modules and the application.
 * 
 * */
public class InitializationContext {
	//the cmd line arguments
	private String[] arguments;
	//the created Guice modules
	private List<Module> guiceModules;
	//the loaded modules to initialize
	private List<IModule> containerModules;
	//the application to run
	private IApplication application;
	//the application configuration of the container
	private ApplicationConfig applicationConfig;
	//the created Guice Injector
	private Injector injector;
	
	public InitializationContext(String[] arguments){
		this.arguments=arguments;
		guiceModules=new ArrayList<Module>();
	}

	/**
	 * Returns the cmd line arguments used by the container.
	 * */
	public String[] getArguments() {
		return arguments;
	}

	/**
	 * Adds a new Guice module that is created by one of the container's module.
	 * */
	public void addGuiceModule(Module module){
		guiceModules.add(module);
	}
	
	/**
	 * Returns all Guice created modules.
	 * */
	public List<Module> getGuiceModules(){
		return guiceModules;
	}
	
	/**
	 * Returns all container's modules.
	 * */
	public List<IModule> getContainerModules() {
		return containerModules;
	}

	/**
	 * Sets the container's modules.
	 * */
	public void setContainerModules(List<IModule> containerModules) {
		this.containerModules = containerModules;
	}

	/**
	 * Returns the application descriptor used to validate the application configuration.
	 * 
	 * @return the application descriptor
	 * */
	public ApplicationDescriptor getCurrentApplicationDescriptor(){
		return application.getDescriptor();
	}

	/**
	 * Returns the application to run.
	 * */
	public IApplication getCurrentApplication() {
		return application;
	}

	/**
	 * Sets the application to run.
	 * */
	public void setCurrentApplication(IApplication application) {
		this.application = application;
	}

	/**
	 * Returns the application configuration.
	 * */
	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	/**
	 * Sets the application configuration.
	 * */
	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	/**
	 * Returns the Guice injector.
	 * */
	public Injector getInjector() {
		return injector;
	}

	/**
	 * Sets the Guice injector.
	 * */
	public void setInjector(Injector injector) {
		this.injector = injector;
	}
}
