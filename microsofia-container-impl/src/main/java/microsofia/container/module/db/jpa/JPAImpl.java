package microsofia.container.module.db.jpa;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

/**
 * Implementation of the JPA resource annotation used when configuring Guice module.
 * */
class JPAImpl extends ResourceAnnotation implements JPA{
	
	public JPAImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return JPA.class;
	}

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof JPA)) {
			 return false;
	     }

		 JPA other = (JPA) o;
	     return name.equals(other.value());
	 }
}
