package microsofia.container.module.endpoint;

import microsofia.container.ContainerException;

public class EndpointException extends ContainerException {
	private static final long serialVersionUID = 0;

	public EndpointException(String m){
		super(m);
	}

	public EndpointException(String m,Throwable th){
		super(m,th);
	}
}
