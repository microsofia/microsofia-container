package microsofia.container.module.db.jdbc;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

/**
 * Implementation of the JDBC annotation, used while configuring Guice bindings.
 * */
class JDBCImpl extends ResourceAnnotation implements JDBC{
	
	public JDBCImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return JDBC.class;
	}

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof JDBC)) {
			 return false;
	     }

	     JDBC other = (JDBC) o;
	     return name.equals(other.value());
	 }
}
