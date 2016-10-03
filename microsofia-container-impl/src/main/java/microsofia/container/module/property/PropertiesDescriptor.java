package microsofia.container.module.property;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * Descriptor of the property module.
 * */
public class PropertiesDescriptor extends ResourceModuleDescriptor<PropertyDescriptor>{
	
	public PropertiesDescriptor(){
	}

	/**
	 * Creates and return a property descriptor, builder style.
	 * */
	public PropertyDescriptor property(String name){
		PropertyDescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new PropertyDescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
