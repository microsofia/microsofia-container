package microsofia.container.module.db.jdbc;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * Descriptor of the JDBC module.
 * */
public class JDBCsDescriptor extends ResourceModuleDescriptor<JDBCDescriptor>{

	public JDBCsDescriptor(){
	}
	
	/**
	 * Creates and return a property descriptor, builder style.
	 * */
	public JDBCDescriptor jdbc(String name){
		JDBCDescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new JDBCDescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
