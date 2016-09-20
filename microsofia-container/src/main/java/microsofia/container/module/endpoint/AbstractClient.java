package microsofia.container.module.endpoint;

public abstract class AbstractClient implements IClient{
	protected boolean started;

	protected AbstractClient(){
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
