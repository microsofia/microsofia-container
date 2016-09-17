package microsofia.container.module.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.module.AbstractModule;

public class PropertyModule extends AbstractModule{
	private Map<String,String> properties;//TODO mix with cmdline

	public PropertyModule(){
		properties=new HashMap<String, String>();
	}

	@Override
	public void preInit(LauncherContext context){
		List<PropertyConfig> properties=context.getCurrentApplicationConfig().getProperties();
		if (properties!=null){
			properties.forEach(it->{
				this.properties.put(it.getName(), it.getValue());
			});
		}
		context.addGuiceModule(new GuicePropertyModule());
	}

	protected class GuicePropertyModule extends com.google.inject.AbstractModule{

		protected GuicePropertyModule(){
		}
		
		@Override
		protected void configure(){
			properties.entrySet().forEach(it->{
				bind(Key.get(String.class,new PropertyImpl(it.getKey()))).toProvider(new PropertyProvider(it.getKey()));
			});
			bind(Key.get(Integer.class,new PropertyImpl("k3"))).toProvider(new IntPropertyProvider("k3"));//TODO introduce PropertyDescriptor
		}
	}

	protected class PropertyProvider implements Provider<String>{
		private String name;
		
		PropertyProvider(String name){
			this.name=name;
		}
		
		@Override
		public String get() {
			return properties.get(name);
		}
		
	}	
	
	protected class IntPropertyProvider implements Provider<Integer>{
		private String name;
		
		IntPropertyProvider(String name){
			this.name=name;
		}
		
		@Override
		public Integer get() {
			return 3;//TODO
		}
		
	}	
}
