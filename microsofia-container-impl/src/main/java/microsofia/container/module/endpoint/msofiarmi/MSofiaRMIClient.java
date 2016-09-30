package microsofia.container.module.endpoint.msofiarmi;

import microsofia.container.module.endpoint.AbstractClient;
import microsofia.rmi.IRegistry;
import microsofia.rmi.IServer;

public class MSofiaRMIClient extends AbstractClient<MSofiaRMIClientConfig>{
	private MSofiaRMIServer server;
	private IServer remoteServer;
	private IRegistry registry;

	public MSofiaRMIClient(MSofiaRMIClientConfig c,MSofiaRMIServer server){
		super(c);
		this.server=server;
	}
	
	@Override
	protected void internalStart() {
		remoteServer=server.getLocalServer().getServer(clientConfig.getRemoteHost(),clientConfig.getRemotePort());
		registry=remoteServer.getRegistry();
	}
	
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		return registry.getObject(id);
	}

	@Override
	protected void internalClose() {
		//TODO notify remote server that this client is off
		//remoteServer
	}
}