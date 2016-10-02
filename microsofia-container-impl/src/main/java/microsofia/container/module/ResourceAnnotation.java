package microsofia.container.module;

import java.lang.annotation.Annotation;

/**
 * Parent class for all resource annotation.
 * */
public abstract class ResourceAnnotation implements Annotation{
	//the resource name
	protected String name;
	
	protected ResourceAnnotation(String name){
		this.name=name;
	}

	/**
	 * The real type of the annotation.
	 * */
	public abstract Class<? extends Annotation> annotationType();

	/**
	 * The resource name.
	 * */
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
