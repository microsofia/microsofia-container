package microsofia.container.module;

import com.google.inject.Injector;

import microsofia.container.LauncherContext;

public abstract class AbstractModule implements IModule{

	protected AbstractModule(){
	}

	@Override
	public void preInit(LauncherContext context){
	}

	@Override
	public void postInit(Injector injector){
	}

	@Override
	public void stop(){
	}
}
