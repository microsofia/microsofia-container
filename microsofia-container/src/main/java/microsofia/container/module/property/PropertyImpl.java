package microsofia.container.module.property;

import java.lang.annotation.Annotation;

public class PropertyImpl implements Property{
	private String name;
	
	public PropertyImpl(String name){
		this.name=name;
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Property.class;
	}

	@Override
	public String value() {
		return name;
	}	

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof Property)) {
			 return false;
	     }

	     Property other = (Property) o;
	     return name.equals(other.value());
	 }

	 @Override 
	 public int hashCode() {
		 return (127 * "value".hashCode()) ^ name.hashCode();
	 }

	 @Override 
	 public String toString() {
		 return "@" + Property.class.getName() + "(value=" + name + ")";
	 }
}
