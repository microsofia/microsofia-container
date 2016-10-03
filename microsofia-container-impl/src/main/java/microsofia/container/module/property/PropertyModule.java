package microsofia.container.module.property;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;

/**
 * The property module is a resource based module. <br/>
 * The module reads the properties values from the application configuration and adds a Guice module in order to inject the properties values.
 * 
 * */
public class PropertyModule extends ResourceBasedModule<PropertyImpl, PropertyConfig,Object,PropertyDescriptor,PropertiesDescriptor> implements IPropertyModule{
	private static final String CMDLINE_PROPERTY = "-property:";
	//map of key_name->value read from cmd line arguments
	private Map<String,String> cmdLineProperties;
	//cache of jaxb context
	private Map<Class<?>,JAXBContext> contexts;

	public PropertyModule(){
		super(Object.class);
		cmdLineProperties=new HashMap<String, String>();
		contexts=new HashMap<>();
	}

	/**
	 * Extra work is done at pre-initialization phase. Parsing the cmd line arguments and filling
	 * a local map of key->value
	 * */
	@Override
	public void preInit(InitializationContext context){
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

	/**
	 * Returns the property module configuration from the context.
	 * */
	@Override
	protected List<PropertyConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getProperties();
	}

	/**
	 * Returns the property module descriptor from the application descriptor.
	 * */
	@Override
	protected PropertiesDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getPropertiesDescriptor();
	}

	/**
	 * Creates a resource from its name and its configuration. In this case, this method
	 * will get the value of the property named name. If the value is a JAXB type, then
	 * it will unmarshal the property element and returns it.
	 * 
	 * */
	@Override
	protected Object createResource(String name, PropertyConfig c) {
		PropertyDescriptor desc=moduleDescriptor.getDescriptor(c.getName());
		if (desc!=null && desc.isTypeObject()){
			//type is JAXB
			if (c.getElement()==null){
				return null;
			}
			try{
				//loading the correct JAXB context
				JAXBContext context=contexts.get(desc.getObjectClass());
				if (context==null){
					context=JAXBContext.newInstance(desc.getObjectClass());
					contexts.put(desc.getObjectClass(), context);
				}
				Unmarshaller unm=context.createUnmarshaller();
				
				//replacing all occurrences of ${key_name} and unmarshaling the resulting string
				return unm.unmarshal(replaceProperties(c.getElement()));
			} catch (Exception e) {
				throw new PropertyException(e.getMessage(), e);
			}
			
		}else{
			//type is not JAXB
			return replaceProperties(c.getValue());
		}
	}

	/*
	 * Transform the element into a string then do the replacement of ${key_name}
	 * */
	private StringReader replaceProperties(Element element) throws Exception{
		StringWriter writer = new StringWriter();
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(element), new StreamResult(writer));
		String xml = writer.toString();
		xml=replaceProperties(xml);
		return new StringReader(xml);
	}

	/**
	 * Property module does nothing at shutdown.
	 * */
	@Override
	protected void stop(Object resource){		
	}

	/**
	 * Returns an implementation of the Property annotation.
	 * */
	@Override
	protected PropertyImpl createResourceAnnotation(String name) {
		return new PropertyImpl(name);
	}
	
	/**
	 * Creates a property Guice module instead of the default one.
	 * */
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuicePropertyModule(context);
	}	
	
	/**
	 * Returns the string value of a property.
	 * */
	@Override
	public String getStringProperty(String name){
		Object obj=getResource(name);
		if (obj==null){
			return null;
		}
		return obj.toString();
	}
	
	/**
	 * Returns the value of a property.
	 * */
	@Override
	public Object getObjectProperty(String name){
		return getResource(name);
	}

	/**
	 * Replaces in the string str all the occurrences of ${key_name} with the value taken
	 * from the command line arguments.
	 * */
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

	/**
	 * The property module has another implementation of the default Guice module.
	 * */
	protected class GuicePropertyModule extends GuiceModule{

		protected GuicePropertyModule(InitializationContext context){
			super(context);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		protected void configure(){
			//binding the public interface to the instance
			bind(IPropertyModule.class).toInstance(PropertyModule.this);

			//we dont call super as resources are of type Object or String
			configs.entrySet().forEach(it->{
				bind(Key.get(String.class,new PropertyImpl(it.getKey()))).toProvider(new StringPropertyProvider(it.getKey()));
			});

			//if the resource has a descriptor then we add additional bindings.
			moduleDescriptor=context.getCurrentApplication().getDescriptor().getPropertiesDescriptor();
			if (moduleDescriptor!=null){
				moduleDescriptor.getDescriptor().values().forEach(it->{
					if (it.isTypeNumeric()){
						//if the property type is numeric, then we add providers for all native numeric types.
						bind(Key.get(Integer.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Integer>(it.getName(),Integer.class));
						bind(Key.get(BigDecimal.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigDecimal>(it.getName(),BigDecimal.class));
						bind(Key.get(BigInteger.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<BigInteger>(it.getName(),BigInteger.class));
						bind(Key.get(Byte.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Byte>(it.getName(),Byte.class));
						bind(Key.get(Double.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Double>(it.getName(),Double.class));
						bind(Key.get(Float.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Float>(it.getName(),Float.class));
						bind(Key.get(Long.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Long>(it.getName(),Long.class));
						bind(Key.get(Short.class,new PropertyImpl(it.getName()))).toProvider(new NumericPropertyProvider<Short>(it.getName(),Short.class));
						
					}else if (it.isTypeObject()){
						//if the property type is a JAXB object, then we bind the real type to an ObjectProvider
						bind(Key.get((Class<Object>)it.getObjectClass(),new PropertyImpl(it.getName()))).toProvider(new ObjectPropertyProvider(it.getName()));
					}
				});
			}			
		}
	}

	/**
	 * Provider that will convert the string value to a numeric type.
	 * */
	protected class NumericPropertyProvider<N extends Number> implements Provider<N>{
		private String name;
		private Constructor<N> cons;
		
		/**
		 * Creates a numeric property provider.
		 * 
		 * @param name the name of the property
		 * @param c the numeric type of the property
		 * */
		NumericPropertyProvider(String name,Class<N> c){
			this.name=name;
			try {
				cons=c.getConstructor(String.class);
			} catch (Exception e) {
				throw new PropertyException(e.getMessage(), e);
			} 
		}

		/**
		 * Gets the string value of the property and converts it to the numeric type.
		 * */
		@Override
		public N get() {
			try {
				return cons.newInstance(getStringProperty(name));
			} catch (Exception e) {
				throw new PropertyException(e.getMessage(), e);
			} 
		}		
	}

	/**
	 * Provider that provides the object value of a property using its name.
	 * */
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
	
	/**
	 * Provider that provides the string value of a property using its name.
	 * */
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
