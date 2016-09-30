package microsofia.container.module;

import microsofia.container.InitializationContext;

/*
 * -TODO : inject JMS
 * -TODO : inject service: RMI ++ (netty)
 * 
 * - TODO : have utilities injection, like ILifecycle interface, etc
 * */
public interface IModule {

	public void preInit(InitializationContext context);

	public void postInit(InitializationContext context);

	public void stop();
}
