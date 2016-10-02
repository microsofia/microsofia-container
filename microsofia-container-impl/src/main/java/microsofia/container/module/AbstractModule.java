package microsofia.container.module;

import microsofia.container.InitializationContext;

/**
 * Abstract class implementing IModule with empty methods.
 * */
public abstract class AbstractModule implements IModule{

	protected AbstractModule(){
	}

	/**
	 * Does nothing.
	 * */
	@Override
	public void preInit(InitializationContext context){
	}

	/**
	 * Does nothing.
	 * */
	@Override
	public void postInit(InitializationContext context){
	}

	/**
	 * Does nothing.
	 * */
	@Override
	public void stop(){
	}
}
