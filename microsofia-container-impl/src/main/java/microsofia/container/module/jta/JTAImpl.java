package microsofia.container.module.jta;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

/**
 * Implementation of the JDBC annotation, used while configuring Guice bindings.
 * */
class JTAImpl extends ResourceAnnotation implements JTA{
	
	public JTAImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return JTA.class;
	}

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof JTA)) {
			 return false;
	     }

	     JTA other = (JTA) o;
	     return name.equals(other.value());
	 }
}
