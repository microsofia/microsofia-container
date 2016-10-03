package microsofia.container.module.endpoint.rest;

import microsofia.container.module.endpoint.Endpoint;

/**
 * REST endpoint implementation.
 * */
public class RestEndpoint extends Endpoint<RestEndpointConfig>{

	public RestEndpoint(RestEndpointConfig config){
		super(config);
		client=new RestClient(config.getClientConfig());
		server=new RestServer(config.getServerConfig());
	}
}
