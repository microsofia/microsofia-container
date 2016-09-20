package microsofia.container.application;

import microsofia.container.module.endpoint.EndpointsDescriptor;

/**
 * TODO: use to configure metadata will be checked before startup for all modules/apps
 * - have nice builder api
 * */
public class ApplicationDescriptor {
	private String type;
	private EndpointsDescriptor endpoints;

	public ApplicationDescriptor(){
		endpoints=new EndpointsDescriptor();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public EndpointsDescriptor getEndpointsDescriptor(){
		return endpoints;
	}
}
