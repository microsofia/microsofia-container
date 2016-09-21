package microsofia.container.application;

import microsofia.container.module.db.jdbc.JDBCsDescriptor;
import microsofia.container.module.db.jpa.JPAsDescriptor;
import microsofia.container.module.endpoint.EndpointsDescriptor;
import microsofia.container.module.property.PropertiesDescriptor;

/**
 * TODO: have nice builder api
 * */
public class ApplicationDescriptor {
	private String type;
	private PropertiesDescriptor propertiesDescriptor;
	private JDBCsDescriptor jdbcsDescriptor;
	private JPAsDescriptor jpasDescriptor;
	private EndpointsDescriptor endpoints;

	public ApplicationDescriptor(){
		propertiesDescriptor=new PropertiesDescriptor();
		jdbcsDescriptor=new JDBCsDescriptor();
		endpoints=new EndpointsDescriptor();
		setJPAsDescriptor(new JPAsDescriptor());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public PropertiesDescriptor getPropertiesDescriptor() {
		return propertiesDescriptor;
	}

	public JDBCsDescriptor getJDBCsDescriptor() {
		return jdbcsDescriptor;
	}

	public JPAsDescriptor getJPAsDescriptor() {
		return jpasDescriptor;
	}

	public void setJPAsDescriptor(JPAsDescriptor jpasDescriptor) {
		this.jpasDescriptor = jpasDescriptor;
	}

	public EndpointsDescriptor getEndpointsDescriptor(){
		return endpoints;
	}
}
