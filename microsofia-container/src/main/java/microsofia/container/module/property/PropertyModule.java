package microsofia.container.module.property;

import java.util.List;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;

//TODO use cmdline in order to do replacement ${}
public class PropertyModule extends ResourceBasedModule<PropertyImpl, PropertyConfig,String> implements IPropertyModule{

	public PropertyModule(){
		super(String.class);
	}
	
	@Override
	protected List<PropertyConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getProperties();
	}

	@Override
	protected String createResource(String name, PropertyConfig c) {
		return c.getValue();
	}
	
	@Override
	protected void stop(String resource){		
	}

	@Override
	protected PropertyImpl createResourceAnnotation(String name) {
		return new PropertyImpl(name);
	}
	
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(LauncherContext context) {
		return new GuicePropertyModule(context);
	}	
	
	public String getProperty(String name){
		return getResource(name);
	}
	
	protected class GuicePropertyModule extends GuiceModule{

		protected GuicePropertyModule(LauncherContext context){
			super(context);
		}
		
		@Override
		protected void configure(){
			bind(IPropertyModule.class).toInstance(PropertyModule.this);
			super.configure();

			bind(Key.get(Integer.class,new PropertyImpl("k3"))).toProvider(new IntPropertyProvider("k3"));//TODO introduce PropertyDescriptor
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
