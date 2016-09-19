package microsofia.container.module;

import java.lang.annotation.Annotation;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;

public abstract class ResourceBasedModule<A extends ResourceAnnotation, C extends ResourceConfig, R> extends AbstractModule{
	protected Class<R> resourceClass;
	protected Map<String,C> configs;
	protected Map<String,R> resources;

	protected ResourceBasedModule(Class<R> resourceClass) {
		this.resourceClass=resourceClass;
		configs=new Hashtable<>();
		resources=new Hashtable<>();
	}

	protected abstract List<C> getResourceConfig(LauncherContext context);
	
	@Override
	public void preInit(LauncherContext context){
		List<C> cs=getResourceConfig(context);
		cs.forEach(it->{
			configs.put(it.getName(), it);
		});

		context.addGuiceModule(createGuiceModule());
	}

	protected com.google.inject.AbstractModule createGuiceModule(){
		return new GuiceModule();
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

		protected GuiceModule(){
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

