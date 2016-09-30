package microsofia.container.module.endpoint;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;

public class ServerImpl extends ResourceAnnotation implements Server{
	
	public ServerImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Server.class;
	}
	
	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof Server)) {
			 return false;
	     }

		 Server other = (Server) o;
	     return name.equals(other.value());
	 }
}
