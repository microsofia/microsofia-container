package microsofia.container.module.endpoint.msofiarmi;

import microsofia.container.module.endpoint.AbstractClient;
import microsofia.rmi.IRegistry;
import microsofia.rmi.IServer;

/**
 * Client endpoint using microsofia-rmi.
 * */
public class MSofiaRMIClient extends AbstractClient<MSofiaRMIClientConfig>{
	//the local server
	private MSofiaRMIServer server;
	//the remote server
	private IServer remoteServer;
	//the remote registry
	private IRegistry registry;

	public MSofiaRMIClient(MSofiaRMIClientConfig c,MSofiaRMIServer server){
		super(c);
		this.server=server;
	}
	
	/**
	 * Starting the client consists of using the local microsofia-rmi to locate the remote server and registry.
	 * */
	@Override
	protected void internalStart() {
		remoteServer=server.getLocalServer().getServer(clientConfig.getRemoteHost(),clientConfig.getRemotePort());
		registry=remoteServer.getRegistry();
	}
	
	/**
	 * Returns a proxy to the remote object by using the remote registry.
	 * */
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		return registry.getObject(id);
	}

	/**
	 * Nothing to do.
	 * */
	@Override
	protected void internalClose() {
		//remark: an enhancement could be to notify the remote server that this client server has stopped
	}
}