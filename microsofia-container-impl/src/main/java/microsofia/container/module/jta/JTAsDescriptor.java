package microsofia.container.module.jta;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * Descriptor of the JTA module.
 * */
public class JTAsDescriptor extends ResourceModuleDescriptor<JTADescriptor>{

	public JTAsDescriptor(){
	}
	
	/**
	 * Creates and return a JTA descriptor, builder style.
	 * */
	public JTADescriptor jta(String name){
		JTADescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new JTADescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
