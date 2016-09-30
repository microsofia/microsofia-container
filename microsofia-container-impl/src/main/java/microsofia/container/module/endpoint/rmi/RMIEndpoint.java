package microsofia.container.module.endpoint.rmi;

import microsofia.container.module.endpoint.Endpoint;

public class RMIEndpoint extends Endpoint<RMIEndpointConfig>{

	public RMIEndpoint(RMIEndpointConfig config){
		super(config);
		client=new RMIClient(config.getClientConfig());
		server=new RMIServer(config.getServerConfig());
	}
}
