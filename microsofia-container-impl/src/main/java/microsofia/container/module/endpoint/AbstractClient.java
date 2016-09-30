package microsofia.container.module.endpoint;

public abstract class AbstractClient<C extends ClientConfig> implements IClient{
	protected boolean started;
	protected C clientConfig;

	protected AbstractClient(C clientConfig){
		this.clientConfig=clientConfig;
	}
	
	@Override
	public Object lookup(String id, Class<?> interf) {
		synchronized(this){
			if (!started){
				internalStart();
				started=true;
			}
		}
		return internalLookup(id,interf);
	}
	
	
	protected abstract void internalStart();

	protected abstract Object internalLookup(String id,Class<?> interf);

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
