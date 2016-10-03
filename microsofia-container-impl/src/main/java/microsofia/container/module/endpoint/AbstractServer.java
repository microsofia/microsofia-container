package microsofia.container.module.endpoint;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Abstract server endpoint.
 * */
public abstract class AbstractServer<S extends ServerConfig> implements IServer{
	//used for lazy startup
	protected boolean started;
	//the server endpoint configuration
	protected S serverConfig;
	//local cache of exported objects
	protected Map<Object, Object> exportedObjects;
	
	protected AbstractServer(S serverConfig){
		this.serverConfig=serverConfig;
		exportedObjects=Collections.synchronizedMap(new IdentityHashMap<>());
	}
	
	/**
	 * The export method does the following: <br/>
	 * <li>if the object is already exported, return</li>
	 * <li>if the server wasn't started, start it and delegate the call to internalExport</li>
	 * */
	@Override
	public void export(String id,Object object,Class<?>[] interfaces){
		if (exportedObjects.containsKey(object)){
			return;//no need to export it twice
		}
		synchronized(this){
			if (!started){
				internalStart();
				started=true;
			}
		}
		internalExport(id,object,interfaces);
		exportedObjects.put(object, object);
	}
	
	/**
	 * The unexport method does the following: <br/>
	 * <li>if the object wasn't exported, skip</li>
	 * <li>if the server wasn't started, then skip otherwise delegate to internalUnexport.</li>
	 * */
	@Override
	public void unexport(String id,Object object){
		Object re=exportedObjects.remove(object);
		if (re==null){
			return;//no need to unexport it, it wasnt exported
		}
		synchronized(this){
			if (!started){
				return;
			}
		}
		internalUnexport(id,object);
		synchronized(this){
			if (exportedObjects.size()==0){
				close();
			}
		}
	}
	
	/**
	 * Method should be implemented to start the server.
	 * */
	protected abstract void internalStart();

	/**
	 * Method should be implemented to export an object.
	 * */
	protected abstract void internalExport(String id,Object object,Class<?>[] interfaces);

	/**
	 * Method should be implemented to unexport an object.
	 * */
	protected abstract void internalUnexport(String id,Object object);
	
	/**
	 * Checks if the server was already started before delegating the call to internalClose.
	 * */
	public void close(){
		synchronized(this){
			if (started){
				internalClose();
				started=false;
			}
		}
	}

	/**
	 * Method should be implemented to stop the server.
	 * */
	protected abstract void internalClose();
}
