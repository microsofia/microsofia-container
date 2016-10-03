package microsofia.container.module.endpoint;

import microsofia.container.module.endpoint.msofiarmi.MSofiaRMIEndpoint;
import microsofia.container.module.endpoint.msofiarmi.MSofiaRMIEndpointConfig;
import microsofia.container.module.endpoint.rest.RestEndpoint;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;
import microsofia.container.module.endpoint.rmi.RMIEndpoint;
import microsofia.container.module.endpoint.rmi.RMIEndpointConfig;

/**
 * Endpoint factory used to create an Endpoint from its configuration.
 * */
public class EndpointFactory {
	
	/**
	 * Returns a new Endpoint from its configuration.
	 * 
	 * @param config the endpoint configuration
	 * @return the newly created endpoint
	 * */
	public static Endpoint<?> createEndpoint(EndpointConfig<?,?> config){
		if (config instanceof RestEndpointConfig){
			return new RestEndpoint((RestEndpointConfig)config);

		}else if (config instanceof MSofiaRMIEndpointConfig){
			return new MSofiaRMIEndpoint((MSofiaRMIEndpointConfig)config);

		}else{
			return new RMIEndpoint((RMIEndpointConfig)config);
		}
	}
}
