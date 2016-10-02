package microsofia.container.application;

import microsofia.container.module.IModule;

/**
 * Interface of an application. An application is a module, has a descriptor and a run method.
 * The module descriptor object contains its meta data.
 * */
public interface IApplication extends IModule{
	
	/**
	 * Returns the application descriptor.
	 * */
	public ApplicationDescriptor getDescriptor();

	/**
	 * Method that will run the application.
	 * */
	public void run() throws Throwable;
}
