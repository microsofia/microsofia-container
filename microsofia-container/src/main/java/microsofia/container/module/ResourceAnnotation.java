package microsofia.container.module;

import java.lang.annotation.Annotation;

public abstract class ResourceAnnotation implements Annotation{
	protected String name;
	
	protected ResourceAnnotation(String name){
		this.name=name;
	}
	
	public abstract Class<? extends Annotation> annotationType();

	public String value() {
		return name;
	}	

	 @Override 
	 public abstract boolean equals(Object o);

	 @Override 
	 public int hashCode() {
		 return (127 * "value".hashCode()) ^ name.hashCode();
	 }

	 @Override 
	 public String toString() {
		 return "@" + getClass().getName() + "(value=" + name + ")";
	}

}
