package microsofia.container.module.endpoint.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import microsofia.container.module.endpoint.AbstractClient;
import microsofia.container.module.endpoint.EndpointException;

public class RMIClient extends AbstractClient<RMIClientConfig>{
	private Registry registry;

	public RMIClient(RMIClientConfig c){
		super(c);
	}
	
	@Override
	protected void internalStart() {
		try {
			registry=LocateRegistry.getRegistry(clientConfig.getHost(), clientConfig.getPort());
		} catch (RemoteException e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		try {
			return registry.lookup(id);
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}

	@Override
	protected void internalClose() {
	}//TODO norlam?
}