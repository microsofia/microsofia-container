package microsofia.container.module.endpoint.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.EndpointException;

public class RMIServer extends AbstractServer<RMIServerConfig>{
	private Registry registry;

	public RMIServer(RMIServerConfig c){
		super(c);
	}
	
	@Override
	protected void internalStart() {
		try{
			registry=LocateRegistry.createRegistry(serverConfig.getPort());
		} catch (RemoteException e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	protected String getServerId(String id,Object object){
		if (id!=null){
			return id;
		}
		for (Class<?> i : object.getClass().getInterfaces()){
			if (Remote.class.isAssignableFrom(i)){
				return i.getName();
			}
		}
		if (object.getClass().getSuperclass()!=null){//TODO factorize all these stuff
			for (Class<?> i : object.getClass().getSuperclass().getInterfaces()){
				if (Remote.class.isAssignableFrom(i)){
					return i.getName();
				}
			}
		}
		throw new EndpointException("Couldn't solve id of instance "+object);
	}

	@Override
	protected void internalExport(String id,Object object,Class<?>[] interfaces) {
		try {
			UnicastRemoteObject.exportObject((Remote)object, serverConfig.getPort());
			registry.rebind(getServerId(id, object), (Remote)object);
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}

	@Override
	protected void internalUnexport(String id,Object object) {
		try {
			UnicastRemoteObject.unexportObject((Remote)object, true);
			registry.unbind(getServerId(id,object));
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}
	}
	
	@Override
	protected void internalClose() {//nothing to do //TODO norlam?
	}
}
