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

/**
 * Parent class for all modules that are resource based.
 * */
public abstract class ResourceBasedModule<A extends ResourceAnnotation, C extends ResourceConfig, R, RD extends ResourceDescriptor, RMD extends ResourceModuleDescriptor<RD>> extends AbstractModule{
	//the class type of the resource
	protected Class<R> resourceClass;
	//the class type of the resource
	protected Class<C> configClass;
	//configuration of the module
	protected Map<String,C> configs;
	//the created resources
	protected Map<String,R> resources;
	//the module descriptor
	protected RMD moduleDescriptor;

	/**
	 * Initializes internal structures and sets the class type of the managed resource.
	 * 
	 * @param resourceClass the type of the resource managed by the module
	 * */
	protected ResourceBasedModule(Class<R> resourceClass,Class<C> configClass) {
		this.resourceClass=resourceClass;
		this.configClass=configClass;
		configs=new Hashtable<>();
		resources=new Hashtable<>();
	}
	
	/**
	 * Stops the module and frees all resources.
	 * */
	@Override
	public void stop(){
		resources.values().forEach(this::stop);
	}

	/**
	 * While stopping the module, this method will be called with every created resource
	 * in order to free its content.
	 * 
	 * @param resource the resource to free
	 * */
	protected abstract void stop(R resource);
	
	/**
	 * Returns the module configuration from the container initialization context.
	 * 
	 * @param context the initialization context
	 * @return configuration of the module
	 * */
	protected abstract List<C> getResourceConfig(InitializationContext context);
	
	/**
	 * Returns the module descriptor from the application descriptor.
	 * 
	 * @param desc the application descriptor
	 * @return the module descriptor
	 * */
	protected abstract RMD getResourceModuleDescriptor(ApplicationDescriptor desc);
	
	/**
	 * Pre-initialization consists of loading the module configuration and creating its Guice module.
	 * */
	@Override
	public void preInit(InitializationContext context){
		//loading the configuration and putting it in a local map
		List<C> cs=getResourceConfig(context);
		cs.forEach(it->{
			configs.put(it.getName(), it);
		});
		
		//checking the existence of required resources
		moduleDescriptor=getResourceModuleDescriptor(context.getCurrentApplication().getDescriptor());
		if (moduleDescriptor!=null){
			moduleDescriptor.getDescriptor().values().forEach(it->{
				if (it.isRequired() && configs.get(it.getName())==null){
					throw new ContainerException("Resource "+it+" is required.");
				}
			});
		}

		//creating and adding a Guice module
		context.addGuiceModule(createGuiceModule(context));
	}

	/**
	 * Post-initialization.
	 * */
	@Override
	public void postInit(InitializationContext context){		

	}

	/**
	 * Creates a default Guice module
	 * */
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context){
		return new GuiceModule(context);
	}

	/**
	 * Creates a resource instance from a name and its configuration.
	 * 
	 * @param name the name of the resource
	 * @param c the configuration of the resource
	 * @return the created resource
	 * */
	protected abstract R createResource(String name,C c);
	
	/**
	 * Creates a resource annotation from its name
	 * 
	 * @param name the resource name
	 * @return the resource annotation
	 * */
	protected abstract A createResourceAnnotation(String name);
	
	/**
	 * Returns a resource by name. If the resource is not already created, then
	 * the method will create it.
	 * 
	 * @param name the resource name
	 * @return the resource
	 * */
	public R getResource(String name){
		//is the resource already created?
		R resource=resources.get(name);
		if (resource!=null){
			return resource;
		}
		
		C c=configs.get(name);
		if (c==null){
			throw new ContainerException("Configuration for resource "+name+" not found.");
		}
		
		synchronized(c){
			resource=resources.get(name);
			if (resource!=null){
				return resource;
			}
			
			resource=createResource(name,c);
			resources.put(name, resource);
		}
		return resource;
	}
	
	/**
	 * Default Guice module that will bind the default resource class and annotation to a default resource provider.
	 * */
	protected class GuiceModule extends com.google.inject.AbstractModule{
		protected InitializationContext context;

		protected GuiceModule(InitializationContext context){
			this.context=context;
		}
		
		@Override
		protected void configure(){			
			configs.entrySet().forEach(it->{
				//for every resource, default bindings
				bind(Key.get(resourceClass,(Annotation)createResourceAnnotation(it.getKey()))).toProvider(new ResourceProvider(it.getKey()));
				bind(Key.get(configClass,(Annotation)createResourceAnnotation(it.getKey()))).toInstance(configs.get(it.getKey()));
			});
		}
	}

	/**
	 * Default provider that will provide the default resource type based on its name.
	 * */
	protected class ResourceProvider extends GenericProvider<R>{
		
		public ResourceProvider(String name){
			super(resourceClass,name);
		}		
	}
	
	/**
	 * Generic provider that will provide the default resource type based on its name.
	 * */
	protected class GenericProvider<O> implements Provider<O>{
		private Class<O> c;
		private String name;
		private R resource;
		
		public GenericProvider(Class<O> c,String name){
			this.c=c;
			this.name=name;
		}
		
		@Override
		public O get() {
			if (resource!=null){
				return c.cast(resource);
			}
			synchronized(this){
				if (resource!=null){
					return c.cast(resource);
				}
				resource=getResource(name);
			}
			return c.cast(resource);
		}		
	}
}

