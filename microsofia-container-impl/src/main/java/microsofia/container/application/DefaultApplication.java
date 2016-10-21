package microsofia.container.application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.google.inject.AbstractModule;

import microsofia.container.Container;
import microsofia.container.InitializationContext;

/**
 * Simple implementation of the IApplication that is configurable.
 * */
public class DefaultApplication extends AbstractApplication{
	@Inject
	protected Container container;
	protected ApplicationDescriptorConfigurator descriptorConfigurator;
	protected Class<?> applicationClass;
	protected Consumer<InitializationContext> initializationCallback;
	protected Consumer<Object> startCallback;
	protected Consumer<Object> stopCallback;
	protected Object application;
	protected List<AbstractModule> guiceModules;
	
	/**
	 * Constructs a DefaultApplication with a type and the class that will be used to create an instance and call
	 * a startCallback on it.
	 * 
	 * @param tye the type of the application
	 * @param applicationClass class to instantiate an object
	 * */
	public DefaultApplication(String type,Class<?> applicationClass){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.type(type);
		descriptorConfigurator=new ApplicationDescriptorConfigurator(applicationDescriptor);
		this.applicationClass=applicationClass;
		guiceModules=new ArrayList<>();
	}
	
	/**
	 * 
	 * Constructs a DefaultApplication with a type.
	 * 
	 * @param tye the type of the application
	 * */
	public DefaultApplication(String type){
		this(type,null);
	}
	
	/**
	 * Sets callback that will be called when pre-initializing the application.
	 * */
	public void setInitializationCallback(Consumer<InitializationContext> initializationCallback){
		this.initializationCallback=initializationCallback;
	}
	
	/**
	 * Sets callback that will be called when running the application.
	 * */
	public void setStartCallback(Consumer<Object> startCallback){
		this.startCallback=startCallback;
	}
	
	/**
	 * Sets callback that will be called when stopping the application.
	 * */
	public void setStopCallback(Consumer<Object> stopCallback){
		this.stopCallback=stopCallback;
	}
	
	/**
	 * Calls the contained application descriptor configurator in order to parse the class
	 * and configure the descriptor.
	 * 
	 * @param c the class to parse
	 * */
	public void parseClass(Class<?> c){
		descriptorConfigurator.parseClass(c);
	}
	
	/**
	 * Adds the application Guice modules.
	 * 
	 * @param module the guice module to add
	 * */
	public void addModule(AbstractModule module){
		guiceModules.add(module);
	}
	
	/**
	 * At pre-initialization, the pre-initialization callback will be called if any and all Guice modules
	 * will be added to the container.
	 * */
	@Override
	public void preInit(InitializationContext context) {
		if (initializationCallback!=null){
			initializationCallback.accept(context);
		}
		guiceModules.forEach(context::addGuiceModule);
	}

	/**
	 * At run, an instance of the applicationClass will be created (if any) and the startCallback will be called
	 * (if any).
	 * */
	@Override
	public void run() throws Throwable{
		if (applicationClass!=null){
			application=container.getInstance(applicationClass);
			if (startCallback!=null){
				startCallback.accept(application);
			}
		}
	}
	
	/**
	 * At stop, the stopCallback will be called if any.
	 * */
	@Override
	public void stop(){
		if (stopCallback!=null){
			stopCallback.accept(application);
		}
	}
}
