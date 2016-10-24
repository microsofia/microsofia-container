package microsofia.container.application;

import microsofia.container.module.atomix.AtomixsDescriptor;
import microsofia.container.module.db.jdbc.JDBCsDescriptor;
import microsofia.container.module.db.jpa.JPAsDescriptor;
import microsofia.container.module.endpoint.EndpointsDescriptor;
import microsofia.container.module.jta.JTAsDescriptor;
import microsofia.container.module.property.PropertiesDescriptor;

/**
 * The application descriptor contains the application and the modules metadatas.
 * */
public class ApplicationDescriptor {
	//the application type
	private String type;
	//properties module descriptor
	private PropertiesDescriptor propertiesDescriptor;
	//jta module descriptor
	private JTAsDescriptor jtasDescriptor;
	//jdbc module descriptor
	private JDBCsDescriptor jdbcsDescriptor;
	//jpa module descriptor
	private JPAsDescriptor jpasDescriptor;
	//endpoint module descriptor
	private EndpointsDescriptor endpoints;
	private AtomixsDescriptor atomixsDescriptor;

	public ApplicationDescriptor(){
		propertiesDescriptor=new PropertiesDescriptor();
		jtasDescriptor=new JTAsDescriptor();
		jdbcsDescriptor=new JDBCsDescriptor();
		endpoints=new EndpointsDescriptor();
		jpasDescriptor=new JPAsDescriptor();
		atomixsDescriptor=new AtomixsDescriptor();
	}

	/**
	 * Returns the application type.
	 * */
	public String getType() {
		return type;
	}

	/**
	 * Sets the application type, builer style.
	 * */
	public ApplicationDescriptor type(String type) {
		setType(type);
		return this;
	}
	
	/**
	 * Sets the application type.
	 * */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Returns the property module descriptor, builder style.
	 * */
	public PropertiesDescriptor properties(){
		return propertiesDescriptor;
	}
	
	/**
	 * Returns the property module descriptor.
	 * */
	public PropertiesDescriptor getPropertiesDescriptor() {
		return propertiesDescriptor;
	}

	/**
	 * Returns the JTA module descriptor, builder style.
	 * */
	public JTAsDescriptor jtas() {
		return jtasDescriptor;
	}
	
	/**
	 * Returns the JTA module descriptor.
	 * */
	public JTAsDescriptor getJTAsDescriptor() {
		return jtasDescriptor;
	}

	/**
	 * Returns the JDBC module descriptor, builder style.
	 * */
	public JDBCsDescriptor jdbcs() {
		return jdbcsDescriptor;
	}
	
	/**
	 * Returns the JDBC module descriptor.
	 * */
	public JDBCsDescriptor getJDBCsDescriptor() {
		return jdbcsDescriptor;
	}
	
	/**
	 * Returns the JPA module descriptor, builder style
	 * */
	public JPAsDescriptor jpas() {
		return jpasDescriptor;
	}

	/**
	 * Returns the JPA module descriptor.
	 * */
	public JPAsDescriptor getJPAsDescriptor() {
		return jpasDescriptor;
	}

	/**
	 * Returns the endpoint module descriptor, builder style
	 * */
	public EndpointsDescriptor endpoints(){
		return endpoints;
	}
	
	/**
	 * Returns the endpoint module descriptor.
	 * */
	public EndpointsDescriptor getEndpointsDescriptor(){
		return endpoints;
	}
	
	public AtomixsDescriptor atomixs() {
		return atomixsDescriptor;
	}

	public AtomixsDescriptor getAtomixsDescriptor() {
		return atomixsDescriptor;
	}
}
