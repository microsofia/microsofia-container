package microsofia.container.module.endpoint.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.EndpointException;

/**
 * RMI server endpoint implementation. It creates locally a registry at a provided port and export
 * objects in them.
 * */
public class RMIServer extends AbstractServer<RMIServerConfig>{
	//Java RMI registry
	private Registry registry;

	public RMIServer(RMIServerConfig c){
		super(c);
	}
	
	/**
	 * Starts locally the RMI registry.
	 * */
	@Override
	protected void internalStart() {
		try{
			registry=LocateRegistry.createRegistry(serverConfig.getPort());
		} catch (RemoteException e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	/**
	 * Exports an RMI object and binds it to the registry.
	 * */
	@Override
	protected void internalExport(String id,Object object,Class<?>[] interfaces) {
		try {
			UnicastRemoteObject.exportObject((Remote)object, serverConfig.getPort());
			registry.rebind(id, (Remote)object);
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}

	/**
	 * Unexports an already exported RMI object.
	 * */
	@Override
	protected void internalUnexport(String id,Object object) {
		try {
			UnicastRemoteObject.unexportObject((Remote)object, true);
			registry.unbind(id);
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	@Override
	protected void internalClose() {//nothing to do, as the RMI registry dies when the JVM exits ...
	}
}
