package microsofia.container.module.atomix;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * Contains the Atomix descriptors by name.
 * */
public class AtomixsDescriptor extends ResourceModuleDescriptor<AtomixDescriptor>{

	public AtomixsDescriptor(){
	}
	
	/**
	 * Creates and returns the Atomix descriptor for a given name.
	 * */
	public AtomixDescriptor atomix(String name){
		AtomixDescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new AtomixDescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
