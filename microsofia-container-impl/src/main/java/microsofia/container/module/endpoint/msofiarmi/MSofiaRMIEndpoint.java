package microsofia.container.module.endpoint.msofiarmi;

import microsofia.container.module.endpoint.Endpoint;

public class MSofiaRMIEndpoint extends Endpoint<MSofiaRMIEndpointConfig>{

	public MSofiaRMIEndpoint(MSofiaRMIEndpointConfig config){
		super(config);
		server=new MSofiaRMIServer(config.getServerConfig());
		client=new MSofiaRMIClient(config.getClientConfig(),(MSofiaRMIServer)server);
	}
}
