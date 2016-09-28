package microsofia.container.module.endpoint;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public abstract class AbstractServer<S extends ServerConfig> implements IServer{
	protected boolean started;
	protected S serverConfig;
	protected Map<Object, Object> exportedObjects;
	
	protected AbstractServer(S serverConfig){
		this.serverConfig=serverConfig;
		exportedObjects=Collections.synchronizedMap(new IdentityHashMap<>());
	}
	
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
	
	protected abstract void internalStart();

	protected abstract void internalExport(String id,Object object,Class<?>[] interfaces);

	protected abstract void internalUnexport(String id,Object object);
	
	public void close(){
		synchronized(this){
			if (started){
				internalClose();
				started=false;
			}
		}
	}

	protected abstract void internalClose();
}
