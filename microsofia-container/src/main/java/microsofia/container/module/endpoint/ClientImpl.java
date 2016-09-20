package microsofia.container.module.endpoint;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

public class ClientImpl extends ResourceAnnotation implements Client{
		
	public ClientImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Client.class;
	}
	
	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof Client)) {
			 return false;
	     }

		 Client other = (Client) o;
	     return name.equals(other.value());
	 }
}

