package microsofia.container.module;

import microsofia.container.LauncherContext;

/*
 * -TODO : inject JMS
 * -TODO : inject service: RMI ++ (netty)
 * 
 * - TODO : have utilities injection, like ILifecycle interface, etc
 * */
public interface IModule {

	public void preInit(LauncherContext context);

	public void postInit(LauncherContext context);

	public void stop();
}
