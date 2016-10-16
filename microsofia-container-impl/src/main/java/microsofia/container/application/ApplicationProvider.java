package microsofia.container.application;

import java.util.List;

import microsofia.container.InitializationContext;

/**
 * Dynamic provider of IApplication.
 * */
public abstract class ApplicationProvider {

	protected ApplicationProvider(){
	}

	/**
	 * Method called by the container in order to load IApplication(s) built during runtime.
	 * */
	public abstract List<IApplication> getApplication(InitializationContext context);
}
