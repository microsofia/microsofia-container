package microsofia.container.module.atomix;

import java.util.ArrayList;
import java.util.List;

import io.atomix.resource.Resource;
import microsofia.container.module.ResourceDescriptor;

/**
 * Descriptor for an Atomix client or replica.
 * */
public class AtomixDescriptor extends ResourceDescriptor{
	private List<Class<? extends Resource<?>>> resourcesClass;
	private IAtomixConfigurator atomixConfigurator;

	public AtomixDescriptor(String name){
		super(name);
		resourcesClass=new ArrayList<>();
	}
	
	/**
	 * Adds an Atomix resource.
	 * */
	public AtomixDescriptor resource(Class<? extends Resource<?>> c){
		resourcesClass.add(c);
		return this;
	}

	/**
	 * Returns the Atomix resources.
	 * */
	public List<Class<? extends Resource<?>>> getResourcesClass() {
		return resourcesClass;
	}

	/**
	 * Sets the Atomix resources.
	 * */
	public void setResourcesClass(List<Class<? extends Resource<?>>> resourcesClass) {
		this.resourcesClass = resourcesClass;
	}

	/**
	 * Sets the Atomix configurator.
	 * */
	public AtomixDescriptor configurator(IAtomixConfigurator c){
		atomixConfigurator=c;
		return this;
	}

	/**
	 * Returns the Atomix configurator.
	 * */
	public IAtomixConfigurator getAtomixConfigurator() {
		return atomixConfigurator;
	}

	/**
	 * Sets the Atomix configurator.
	 * */
	public void setAtomixConfigurator(IAtomixConfigurator atomixConfigurator) {
		this.atomixConfigurator = atomixConfigurator;
	}
}
