package microsofia.container.application;

import microsofia.container.module.db.jdbc.JDBCsDescriptor;
import microsofia.container.module.db.jpa.JPAsDescriptor;
import microsofia.container.module.endpoint.EndpointsDescriptor;
import microsofia.container.module.property.PropertiesDescriptor;

//TODO: have nice builder api
/**
 * The application descriptor contains the application and the modules metadatas.
 * 
 * */
public class ApplicationDescriptor {
	//the application type
	private String type;
	//properties module descriptor
	private PropertiesDescriptor propertiesDescriptor;
	//jdbc module descriptor
	private JDBCsDescriptor jdbcsDescriptor;
	//jpa module descriptor
	private JPAsDescriptor jpasDescriptor;
	//endpoint module descriptor
	private EndpointsDescriptor endpoints;

	public ApplicationDescriptor(){
		propertiesDescriptor=new PropertiesDescriptor();
		jdbcsDescriptor=new JDBCsDescriptor();
		endpoints=new EndpointsDescriptor();
		jpasDescriptor=new JPAsDescriptor();
	}

	/**
	 * Returns the application type.
	 * */
	public String getType() {
		return type;
	}

	/**
	 * Sets the application type.
	 * */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Returns the property module descriptor.
	 * */
	public PropertiesDescriptor getPropertiesDescriptor() {
		return propertiesDescriptor;
	}

	/**
	 * Returns the JDBC module descriptor.
	 * */
	public JDBCsDescriptor getJDBCsDescriptor() {
		return jdbcsDescriptor;
	}

	/**
	 * Returns the JPA module descriptor.
	 * */
	public JPAsDescriptor getJPAsDescriptor() {
		return jpasDescriptor;
	}

	/**
	 * Returns the endpoint module descriptor.
	 * */
	public EndpointsDescriptor getEndpointsDescriptor(){
		return endpoints;
	}
}
