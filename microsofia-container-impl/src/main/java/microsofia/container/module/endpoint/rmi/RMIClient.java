package microsofia.container.module.endpoint.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import microsofia.container.module.endpoint.AbstractClient;
import microsofia.container.module.endpoint.EndpointException;

/**
 * RMI client endpoint implementation. It locates the remote registry used to lookup remote RMI objects.
 * */
public class RMIClient extends AbstractClient<RMIClientConfig>{
	//the remote registry
	private Registry registry;

	public RMIClient(RMIClientConfig c){
		super(c);
	}
	
	/**
	 * Locates the remote registry.
	 * */
	@Override
	protected void internalStart() {
		try {
			registry=LocateRegistry.getRegistry(clientConfig.getHost(), clientConfig.getPort());
		} catch (RemoteException e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	/**
	 * Lookups in the registry the remote object.
	 * */
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		try {
			return registry.lookup(id);
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}

	/**
	 * Nothing to do.
	 * */
	@Override
	protected void internalClose() {
	}
}