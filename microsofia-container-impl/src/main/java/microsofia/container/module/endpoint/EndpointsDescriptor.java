package microsofia.container.module.endpoint;

import microsofia.container.module.ResourceModuleDescriptor;

/**
 * Endpoint module descriptor.
 * */
public class EndpointsDescriptor extends ResourceModuleDescriptor<EndpointDescriptor>{

	public EndpointsDescriptor(){
	}
	
	/**
	 * Creates and return a endpoint descriptor, builder style.
	 * */
	public EndpointDescriptor endpoint(String name){
		EndpointDescriptor desc=getDescriptor(name);
		if (desc==null){
			desc=new EndpointDescriptor(name);
			addDescriptor(desc);
		}
		return desc;
	}
}
