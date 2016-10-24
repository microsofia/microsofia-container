package microsofia.container.module.atomix;

import microsofia.container.module.ResourceModuleDescriptor;

public class AtomixsDescriptor extends ResourceModuleDescriptor<AtomixDescriptor>{

	public AtomixsDescriptor(){
	}
	
	public AtomixDescriptor atomix(String name){
		AtomixDescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new AtomixDescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
