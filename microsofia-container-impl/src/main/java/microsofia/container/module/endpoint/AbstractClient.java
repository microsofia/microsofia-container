package microsofia.container.module.endpoint;

/**
 * Abstract client endpoint.
 * */
public abstract class AbstractClient<C extends ClientConfig> implements IClient{
	//used for lazy startup
	protected boolean started;
	//the client endpoint configuration
	protected C clientConfig;

	protected AbstractClient(C clientConfig){
		this.clientConfig=clientConfig;
	}
	
	/**
	 * The lookup initializes the client if not already the case and then calls the internalLookup.
	 * 
	 * */
	@Override
	public Object lookup(String id, Class<?> interf) {
		synchronized(this){
			//lazy startup
			if (!started){
				internalStart();
				started=true;
			}
		}
		return internalLookup(id,interf);
	}
	
	/**
	 * Method should be implemented to start the client endpoint.
	 * */
	protected abstract void internalStart();

	/**
	 * Method sohould be implemented to lookup for a remote object.
	 * 
	 * */
	protected abstract Object internalLookup(String id,Class<?> interf);

	/**
	 * If the client not started, the method wont close anything.
	 * It the client is started, it will delegate the call to internalClose.
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
	 * Method should be implemented to close the client endpoint.
	 * */
	protected abstract void internalClose();

}
