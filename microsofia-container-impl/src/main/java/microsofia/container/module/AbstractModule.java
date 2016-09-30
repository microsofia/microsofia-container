package microsofia.container.module;

import microsofia.container.InitializationContext;

public abstract class AbstractModule implements IModule{

	protected AbstractModule(){
	}

	@Override
	public void preInit(InitializationContext context){
	}

	@Override
	public void postInit(InitializationContext context){
	}

	@Override
	public void stop(){
	}
}
