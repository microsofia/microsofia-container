package microsofia.container.application;

import microsofia.container.module.AbstractModule;

/**
 * Abstract application that implements IApplication with empty methods.
 * */
public abstract class AbstractApplication extends AbstractModule implements IApplication{
	protected ApplicationDescriptor applicationDescriptor;
	
	protected AbstractApplication(){
		setDescriptor(new ApplicationDescriptor());
	}

	/**
	 * Returns the application descriptor.
	 * */
	@Override
	public ApplicationDescriptor getDescriptor() {
		return applicationDescriptor;
	}

	/**
	 * Sets the application descriptor.
	 * */
	public void setDescriptor(ApplicationDescriptor applicationDescriptor) {
		this.applicationDescriptor = applicationDescriptor;
	}
}
