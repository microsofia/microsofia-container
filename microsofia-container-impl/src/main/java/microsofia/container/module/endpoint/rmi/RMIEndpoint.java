package microsofia.container.module.endpoint.rmi;

import microsofia.container.module.endpoint.Endpoint;

/**
 * RMI endpoint implementation. It uses native Java RMI in order to export objects and locate them remotely.
 * */
public class RMIEndpoint extends Endpoint<RMIEndpointConfig>{

	public RMIEndpoint(RMIEndpointConfig config){
		super(config);
		client=new RMIClient(config.getClientConfig());
		server=new RMIServer(config.getServerConfig());
	}
}
