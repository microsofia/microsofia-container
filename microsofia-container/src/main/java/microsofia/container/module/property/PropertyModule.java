package microsofia.container.module.property;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;

public class PropertyModule extends ResourceBasedModule<PropertyImpl, PropertyConfig,Object,PropertyDescriptor,PropertiesDescriptor> implements IPropertyModule{
	private static final String CMDLINE_PROPERTY = "-property:";
	private Map<String,String> cmdLineProperties;
	private Map<Class<?>,JAXBContext> contexts;
	private PropertiesDescriptor propertiesDescriptor;

	public PropertyModule(){
		super(Object.class);
		cmdLineProperties=new HashMap<String, String>();
		contexts=new HashMap<>();
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
	protected List<PropertyConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getProperties();
	}
	

	@Override
	protected PropertiesDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getPropertiesDescriptor();
	}

	@Override
	protected Object createResource(String name, PropertyConfig c) {
		PropertyDescriptor desc=propertiesDescriptor.getDescriptor(c.getName());
		if (desc!=null && desc.isTypeObject()){
			if (c.getElement()==null){
				return null;
			}
			try{
				JAXBContext context=contexts.get(desc.getObjectClass());
				if (context==null){
					context=JAXBContext.newInstance(desc.getObjectClass());
					contexts.put(desc.getObjectClass(), context);
				}
				Unmarshaller unm=context.createUnmarshaller();//TODO do string replacement within the complex object
				return unm.unmarshal(c.getElement());
			} catch (Exception e) {
				throw new PropertyException(e.getMessage(), e);
			}
			
		}else{
			return replaceProperties(c.getValue());
		}
	}
	
	@Override
	protected void stop(Object resource){		
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
	public String getStringProperty(String name){
		Object obj=getResource(name);
		if (obj==null){
			return null;
		}
		return obj.toString();
	}
	
	@Override
	public Object getObjectProperty(String name){
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
		
		@SuppressWarnings("unchecked")
		@Override
		protected void configure(){
			bind(IPropertyModule.class).toInstance(PropertyModule.this);

			//we dont call super as resources are of type Object or String
			configs.entrySet().forEach(it->{
				bind(Key.get(String.class,(Annotation)createResourceAnnotation(it.getKey()))).toProvider(new StringPropertyProvider(it.getKey()));
			});

			propertiesDescriptor=context.getCurrentApplication().getDescriptor().getPropertiesDescriptor();
			if (propertiesDescriptor!=null){
				propertiesDescriptor.getDescriptor().values().forEach(it->{
					if (it.isTypeNumeric()){
						bind(Key.get(Integer.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Integer>(it.getName(),Integer.class));
						bind(Key.get(BigDecimal.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigDecimal>(it.getName(),BigDecimal.class));
						bind(Key.get(BigInteger.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigInteger>(it.getName(),BigInteger.class));
						bind(Key.get(Byte.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Byte>(it.getName(),Byte.class));
						bind(Key.get(Double.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Double>(it.getName(),Double.class));
						bind(Key.get(Float.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Float>(it.getName(),Float.class));
						bind(Key.get(Long.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Long>(it.getName(),Long.class));
						bind(Key.get(Short.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Short>(it.getName(),Short.class));
						
					}else if (it.isTypeObject()){
						bind(Key.get((Class<Object>)it.getObjectClass(),new PropertyImpl(it.getName()))).toProvider(new ObjectPropertyProvider(it.getName()));
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
				throw new PropertyException(e.getMessage(), e);
			} 
		}
		
		@Override
		public N get() {
			try {
				return cons.newInstance(getStringProperty(name));
			} catch (Exception e) {
				throw new PropertyException(e.getMessage(), e);
			} 
		}		
	}
	
	protected class ObjectPropertyProvider implements Provider<Object>{
		private String name;
		
		ObjectPropertyProvider(String name){
			this.name=name;
		}
		
		@Override
		public Object get() {
			return getObjectProperty(name); 
		}		
	}
	
	protected class StringPropertyProvider implements Provider<String>{
		private String name;
		
		StringPropertyProvider(String name){
			this.name=name;
		}
		
		@Override
		public String get() {
			return getStringProperty(name); 
		}		
	}
}
