package microsofia.container.module;

import microsofia.container.InitializationContext;

/*
 * - TODO : inject JMS
 * - TODO : have utilities injection, like ILifecycle interface, Injector wrapper, etc
 * */
/**
 * Interface of microsofia's container module.
 * 
 * */
public interface IModule {

	/**
	 * Method called during the first phase of pre-initialization in order to allow
	 * every module to initialize itself and for example set up its Guice modules.
	 * */
	public void preInit(InitializationContext context);

	/**
	 * Method called during the second phase, at post-initialization.
	 * 
	 * */
	public void postInit(InitializationContext context);

	/**
	 * Method called at container shutdown to free all module's resources.
	 * */
	public void stop();
}
