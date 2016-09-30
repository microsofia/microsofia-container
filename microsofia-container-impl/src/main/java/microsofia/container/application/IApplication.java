package microsofia.container.application;

import microsofia.container.module.IModule;

public interface IApplication extends IModule{
	
	public ApplicationDescriptor getDescriptor();

	public void run() throws Throwable;
}
