package microsofia.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.inject.Module;

import microsofia.container.application.ApplicationConfig;
import microsofia.container.module.IModule;

public class LauncherContext {
	private List<Module> guiceModules;
	private List<IModule> coreModules;
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

	//not extra, think of better way
	public <T> T getCoreModule(Class<T> c) {
		Optional<T> op=coreModules.stream().filter(it->c.isAssignableFrom(it.getClass()))
								           .map(it->{return c.cast(it);})
								           .findFirst();
		if (!op.isPresent()){
			throw new IllegalStateException("Could not find module of type "+c);
		}
		return op.get();
	}
	
	public List<IModule> getCoreModules() {
		return coreModules;
	}

	public void setCoreModules(List<IModule> coreModules) {
		this.coreModules = coreModules;
	}

	public ApplicationConfig getCurrentApplicationConfig() {
		return applicationConfig;
	}

	public void setCurrentApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}
}
