package microsofia.container.module.endpoint;

import microsofia.container.module.endpoint.rest.RestEndpoint;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;

public class EndpointFactory {
	
	public static Endpoint createEndpoint(EndpointConfig config){
		return new RestEndpoint((RestEndpointConfig)config);
	}
}
