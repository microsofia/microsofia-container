package microsofia.container.module.property;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

class PropertyImpl extends ResourceAnnotation implements Property{
	
	public PropertyImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Property.class;
	}

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof Property)) {
			 return false;
	     }

	     Property other = (Property) o;
	     return name.equals(other.value());
	 }
}
