package microsofia.container;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Module;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.module.IModule;

public class LauncherContext {
	private List<Module> guiceModules;
	private List<IModule> containerModules;
	private ApplicationConfig applicationConfig;
	
	public LauncherContext(){
		guiceModules=new ArrayList<Module>();
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

	public ApplicationConfig getCurrentApplicationConfig() {
		return applicationConfig;
	}

	public void setCurrentApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
