package microsofia.container.module.db.jpa;

import microsofia.container.ContainerException;

public class JPAException extends ContainerException {
	private static final long serialVersionUID = 0;

	public JPAException(String m){
		super(m);
	}

	public JPAException(String m,Throwable th){
		super(m,th);
	}
}

