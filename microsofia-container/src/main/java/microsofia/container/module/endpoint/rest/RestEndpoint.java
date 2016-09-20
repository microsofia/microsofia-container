package microsofia.container.module.endpoint.rest;

import microsofia.container.module.endpoint.Endpoint;

public class RestEndpoint extends Endpoint<RestEndpointConfig>{

	public RestEndpoint(RestEndpointConfig config){
		super(config);
		client=new RestClient();
		server=new RestServer();
	}
}
