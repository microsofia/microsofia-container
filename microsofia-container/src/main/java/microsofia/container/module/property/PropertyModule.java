package microsofia.container.module.property;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;

public class PropertyModule extends ResourceBasedModule<PropertyImpl, PropertyConfig,String> implements IPropertyModule{
	private static final String CMDLINE_PROPERTY = "-property:";
	private Map<String,String> cmdLineProperties;

	public PropertyModule(){
		super(String.class);
		cmdLineProperties=new HashMap<String, String>();
	}
	
	@Override
	public void preInit(LauncherContext context){
		super.preInit(context);
		
		//initializing properties replacement
		if (context.getArguments()!=null){
			for (String arg : context.getArguments()){
				if (arg.trim().startsWith(CMDLINE_PROPERTY)){
					String s=arg.trim().substring(CMDLINE_PROPERTY.length());
					int index=s.indexOf("=");
					if (index>0){
						String key=s.substring(0,index);
						String value=s.substring(index+1, s.length());
						cmdLineProperties.put(key, value);
					}
				}
			}
		}
	}
	
	@Override
	public void postInit(LauncherContext context){		
		//checking the existence of required properties
		PropertiesDescriptor propertiesDescriptor=context.getCurrentApplication().getDescriptor().getPropertiesDescriptor();
		if (propertiesDescriptor!=null){
			propertiesDescriptor.getPropertiesDescriptor().values().forEach(it->{
				if (it.isRequired() && configs.get(it.getName())==null){
					throw new RuntimeException("Property "+it.getName()+" is required.");//TODO collect in context
				}
			});
		}
	}
	
	@Override
	protected List<PropertyConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getProperties();
	}

	@Override
	protected String createResource(String name, PropertyConfig c) {
		return replaceProperties(c.getValue());
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
	
	@Override
	public String getProperty(String name){
		return getResource(name);
	}
	
	@Override
	public String replaceProperties(String str){
		if (str==null){
			return str;
		}
		int indexStart=str.indexOf("${");
		while (indexStart>=0){
			int indexEnd=str.indexOf("}",indexStart+1);
			if (indexEnd>indexStart){
				String key=str.substring(indexStart+2,indexEnd);
				String value=cmdLineProperties.get(key);
				if (value!=null){
					str=str.replaceAll("\\$\\{"+key+"\\}", value);
					indexStart=str.indexOf("${");

				}else{
					indexStart=indexEnd;
				}				
			}else{
				break;
			}
		}
		return str;
	}
	
	protected class GuicePropertyModule extends GuiceModule{

		protected GuicePropertyModule(LauncherContext context){
			super(context);
		}
		
		@Override
		protected void configure(){
			bind(IPropertyModule.class).toInstance(PropertyModule.this);
			super.configure();

			PropertiesDescriptor propertiesDescriptor=context.getCurrentApplication().getDescriptor().getPropertiesDescriptor();
			if (propertiesDescriptor!=null){
				propertiesDescriptor.getPropertiesDescriptor().values().forEach(it->{
					if (it.isTypeNumeric()){
						bind(Key.get(Integer.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Integer>(it.getName(),Integer.class));
						bind(Key.get(BigDecimal.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigDecimal>(it.getName(),BigDecimal.class));
						bind(Key.get(BigInteger.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigInteger>(it.getName(),BigInteger.class));
						bind(Key.get(Byte.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Byte>(it.getName(),Byte.class));
						bind(Key.get(Double.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Double>(it.getName(),Double.class));
						bind(Key.get(Float.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Float>(it.getName(),Float.class));
						bind(Key.get(Long.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Long>(it.getName(),Long.class));
						bind(Key.get(Short.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Short>(it.getName(),Short.class));
					}
				});
			}			
		}
	}
	
	protected class NumericPropertyProvider<N extends Number> implements Provider<N>{
		private String name;
		private Constructor<N> cons;
		
		NumericPropertyProvider(String name,Class<N> c){
			this.name=name;
			try {
				cons=c.getConstructor(String.class);
			} catch (Exception e) {
				e.printStackTrace();//TODO
			} 
		}
		
		@Override
		public N get() {
			try {
				return cons.newInstance(getProperty(name));
			} catch (Exception e) {
				// TODO throw PropertyException
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			} 
		}		
	}
}
