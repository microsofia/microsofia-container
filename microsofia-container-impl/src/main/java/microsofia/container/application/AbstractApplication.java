package microsofia.container.application;

import microsofia.container.module.AbstractModule;

public abstract class AbstractApplication extends AbstractModule implements IApplication{
	protected ApplicationDescriptor applicationDescriptor;
	
	protected AbstractApplication(){
		setDescriptor(new ApplicationDescriptor());
	}

	@Override
	public ApplicationDescriptor getDescriptor() {
		return applicationDescriptor;
	}

	public void setDescriptor(ApplicationDescriptor applicationDescriptor) {
		this.applicationDescriptor = applicationDescriptor;
	}
}
