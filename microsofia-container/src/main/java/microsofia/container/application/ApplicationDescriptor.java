package microsofia.container.application;

import microsofia.container.module.endpoint.EndpointsDescriptor;
import microsofia.container.module.property.PropertiesDescriptor;

/**
 * TODO: use to configure metadata will be checked before startup for all modules/apps
 * - have nice builder api
 * */
public class ApplicationDescriptor {
	private String type;
	private PropertiesDescriptor propertiesDescriptor;
	private EndpointsDescriptor endpoints;

	public ApplicationDescriptor(){
		propertiesDescriptor=new PropertiesDescriptor();
		endpoints=new EndpointsDescriptor();
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

	public void setPropertiesDescriptor(PropertiesDescriptor propertiesDescriptor) {
		this.propertiesDescriptor = propertiesDescriptor;
	}

	public EndpointsDescriptor getEndpointsDescriptor(){
		return endpoints;
	}
}
