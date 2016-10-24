package microsofia.container.module.atomix;

import java.lang.annotation.Annotation;

import microsofia.container.module.ResourceAnnotation;
import microsofia.container.module.atomix.Cluster;

class ClusterImpl extends ResourceAnnotation implements Cluster{
	
	public ClusterImpl(String name){
		super(name);
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Cluster.class;
	}

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof Cluster)) {
			 return false;
	     }

	     Cluster other = (Cluster) o;
	     return name.equals(other.value());
	 }
}
