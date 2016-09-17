package microsofia.container.module.db.jpa;

import java.lang.annotation.Annotation;

public class JPAImpl implements JPA{
	private String name;
	
	public JPAImpl(String name){
		this.name=name;
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return JPA.class;
	}

	@Override
	public String value() {
		return name;
	}	

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof JPA)) {
			 return false;
	     }

		 JPA other = (JPA) o;
	     return name.equals(other.value());
	 }

	 @Override 
	 public int hashCode() {
		 return (127 * "value".hashCode()) ^ name.hashCode();
	 }

	 @Override 
	 public String toString() {
		 return "@" + JPA.class.getName() + "(value=" + name + ")";
	 }
}
