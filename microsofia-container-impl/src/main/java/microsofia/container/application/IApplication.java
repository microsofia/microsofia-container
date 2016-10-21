package microsofia.container.application;

import microsofia.container.module.IModule;

/**
 * Interface of an application. An application is a module, has a descriptor and a run method.
 * The application descriptor contains its metadata. The container will service load all the 
 * implementation of IApplication in order to find the one to run.
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
