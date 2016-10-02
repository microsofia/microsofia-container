package microsofia.container;

/**
 * Parent class exception for all exceptions thrown by microsofia container.
 * */
public class ContainerException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ContainerException(String m){
		super(m);
	}

	public ContainerException(String m,Throwable th){
		super(m,th);
	}
}
