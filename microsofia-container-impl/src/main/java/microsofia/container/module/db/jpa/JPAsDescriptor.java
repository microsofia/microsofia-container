package microsofia.container.module.db.jpa;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * JPA module descriptor.
 * */
public class JPAsDescriptor extends ResourceModuleDescriptor<JPADescriptor>{

	public JPAsDescriptor(){
	}

	/**
	 * Creates and return a JPA descriptor, builder style.
	 * */
	public JPADescriptor jpa(String name){
		JPADescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new JPADescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
