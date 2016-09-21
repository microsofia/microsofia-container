package microsofia.container.module.endpoint;

import microsofia.container.module.endpoint.rest.RestEndpoint;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;
import microsofia.container.module.endpoint.rmi.RMIEndpoint;
import microsofia.container.module.endpoint.rmi.RMIEndpointConfig;

public class EndpointFactory {
	
	public static Endpoint createEndpoint(EndpointConfig config){
		if (config instanceof RestEndpointConfig){
			return new RestEndpoint((RestEndpointConfig)config);
		}else{
			return new RMIEndpoint((RMIEndpointConfig)config);
		}
	}
}
