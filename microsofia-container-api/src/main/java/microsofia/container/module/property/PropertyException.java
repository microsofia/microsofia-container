package microsofia.container.module.property;

import microsofia.container.ContainerException;

public class PropertyException extends ContainerException {
	private static final long serialVersionUID = 0;

	public PropertyException(String m){
		super(m);
	}

	public PropertyException(String m,Throwable th){
		super(m,th);
	}
}
