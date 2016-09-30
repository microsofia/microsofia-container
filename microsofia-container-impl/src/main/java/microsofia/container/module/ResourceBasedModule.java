package microsofia.container.module;

import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.ContainerException;
import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;

public abstract class ResourceBasedModule<A extends ResourceAnnotation, C extends ResourceConfig, R, RD extends ResourceDescriptor, RMD extends ResourceModuleDescriptor<RD>> extends AbstractModule{
	protected Class<R> resourceClass;
	protected Map<String,C> configs;
	protected Map<String,R> resources;
	protected RMD moduleDescriptor;

	protected ResourceBasedModule(Class<R> resourceClass) {
		this.resourceClass=resourceClass;
		configs=new Hashtable<>();
		resources=new Hashtable<>();
	}
	
	@Override
	public void stop(){
		resources.values().forEach(this::stop);
	}

	protected abstract void stop(R resource);
	
	protected abstract List<C> getResourceConfig(InitializationContext context);
	
	protected abstract RMD getResourceModuleDescriptor(ApplicationDescriptor desc);
		
	@Override
	public void preInit(InitializationContext context){
		List<C> cs=getResourceConfig(context);
		cs.forEach(it->{
			configs.put(it.getName(), it);
		});

		context.addGuiceModule(createGuiceModule(context));
	}
	
	@Override
	public void postInit(InitializationContext context){		
		//checking the existence of required properties
		moduleDescriptor=getResourceModuleDescriptor(context.getCurrentApplication().getDescriptor());
		if (moduleDescriptor!=null){
			moduleDescriptor.getDescriptor().values().forEach(it->{
				if (it.isRequired() && configs.get(it.getName())==null){
					throw new ContainerException("Resource "+it+" is required.");
				}
			});
		}
	}

	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context){
		return new GuiceModule(context);
	}

	protected abstract R createResource(String name,C c);
	
	protected abstract A createResourceAnnotation(String name);
	
	public R getResource(String name){
		R resource=resources.get(name);
		if (resource!=null){
			return resource;
		}
		synchronized(this){
			resource=resources.get(name);
			if (resource!=null){
				return resource;
			}
			
			C c=configs.get(name);

			resource=createResource(name,c);
			resources.put(name, resource);
		}
		return resource;
	}
	
	protected class GuiceModule extends com.google.inject.AbstractModule{
		protected InitializationContext context;

		protected GuiceModule(InitializationContext context){
			this.context=context;
		}
		
		@Override
		protected void configure(){			
			configs.entrySet().forEach(it->{
				bind(Key.get(resourceClass,(Annotation)createResourceAnnotation(it.getKey()))).toProvider(new ResourceProvider(it.getKey()));
			});
		}
	}

	protected class ResourceProvider implements Provider<R>{
		private String name;
		private R resource;
		
		ResourceProvider(String name){
			this.name=name;
		}
		
		@Override
		public R get() {
			if (resource!=null){
				return resource;
			}
			synchronized(this){
				if (resource!=null){
					return resource;
				}
				resource=getResource(name);
			}
			return resource;
		}
		
	}
}

