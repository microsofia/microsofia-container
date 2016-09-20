package microsofia.container.module.endpoint;

public abstract class AbstractServer implements IServer{
	protected boolean started;
	
	protected AbstractServer(){
	}
	
	@Override
	public void export(Object object){
		synchronized(this){
			if (!started){
				internalStart();
				started=true;
			}
		}
		internalExport(object);
	}
	
	protected abstract void internalStart();

	protected abstract void internalExport(Object object);

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
