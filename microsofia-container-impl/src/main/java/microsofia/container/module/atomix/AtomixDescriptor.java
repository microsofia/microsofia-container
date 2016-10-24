package microsofia.container.module.atomix;

import java.util.ArrayList;
import java.util.List;

import io.atomix.resource.Resource;
import microsofia.container.module.ResourceDescriptor;

public class AtomixDescriptor extends ResourceDescriptor{
	private List<Class<? extends Resource<?>>> resourcesClass;
	private IAtomixConfigurator atomixConfigurator;

	public AtomixDescriptor(String name){
		super(name);
		resourcesClass=new ArrayList<>();
	}
	
	public AtomixDescriptor resource(Class<? extends Resource<?>> c){
		resourcesClass.add(c);
		return this;
	}

	public List<Class<? extends Resource<?>>> getResourcesClass() {
		return resourcesClass;
	}

	public void setResourcesClass(List<Class<? extends Resource<?>>> resourcesClass) {
		this.resourcesClass = resourcesClass;
	}
	
	public AtomixDescriptor configurator(IAtomixConfigurator c){
		atomixConfigurator=c;
		return this;
	}

	public IAtomixConfigurator getAtomixConfigurator() {
		return atomixConfigurator;
	}

	public void setAtomixConfigurator(IAtomixConfigurator atomixConfigurator) {
		this.atomixConfigurator = atomixConfigurator;
	}
}
