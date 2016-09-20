package microsofia.container;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Module;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.IApplication;
import microsofia.container.module.IModule;

public class LauncherContext {
	private String[] arguments;
	private List<Module> guiceModules;
	private List<IModule> containerModules;
	private IApplication application;
	private ApplicationConfig applicationConfig;
	private Injector injector;
	
	public LauncherContext(String[] arguments){
		this.arguments=arguments;
		guiceModules=new ArrayList<Module>();
	}
	
	public String[] getArguments() {
		return arguments;
	}

	public void addGuiceModule(Module module){
		guiceModules.add(module);
	}
	
	public List<Module> getGuiceModules(){
		return guiceModules;
	}
	
	public List<IModule> getContainerModules() {
		return containerModules;
	}

	public void setContainerModules(List<IModule> containerModules) {
		this.containerModules = containerModules;
	}

	public ApplicationDescriptor getCurrentApplicationDescriptor(){
		return application.getDescriptor();
	}
	
	public IApplication getCurrentApplication() {
		return application;
	}

	public void setCurrentApplication(IApplication application) {
		this.application = application;
	}

	public ApplicationConfig getCurrentApplicationConfig() {
		return applicationConfig;
	}

	public void setCurrentApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public Injector getInjector() {
		return injector;
	}

	public void setInjector(Injector injector) {
		this.injector = injector;
	}
}
