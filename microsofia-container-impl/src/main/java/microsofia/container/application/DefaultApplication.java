package microsofia.container.application;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import com.google.inject.AbstractModule;

import microsofia.container.Container;
import microsofia.container.InitializationContext;

//TODO doc
public class DefaultApplication extends AbstractApplication{
	@Inject
	protected Container container;
	protected ApplicationDescriptorBuilder descriptorBuilder;
	protected Class<?> applicationClass;
	protected Consumer<InitializationContext> initialization;
	protected Consumer<Object> startCallback;
	protected Consumer<Object> stopCallback;
	protected Object application;
	protected List<AbstractModule> guiceModules;
	
	public DefaultApplication(String type,Class<?> applicationClass){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.type(type);
		descriptorBuilder=new ApplicationDescriptorBuilder(applicationDescriptor);
		this.applicationClass=applicationClass;
		guiceModules=new ArrayList<>();
	}
	
	public DefaultApplication(String type){
		this(type,null);
	}
	
	public void setInitialization(Consumer<InitializationContext> initialization){
		this.initialization=initialization;
	}
	
	public void setStartCallback(Consumer<Object> startCallback){
		this.startCallback=startCallback;
	}
	
	public void setStopCallback(Consumer<Object> stopCallback){
		this.stopCallback=stopCallback;
	}
	
	public void addClass(Class<?> c){
		descriptorBuilder.visit(c);
	}
	
	public void addModule(AbstractModule module){
		guiceModules.add(module);
	}
	
	@Override
	public void preInit(InitializationContext context) {
		if (initialization!=null){
			initialization.accept(context);
		}
		guiceModules.forEach(context::addGuiceModule);
	}

	@Override
	public void run() throws Throwable{
		if (applicationClass!=null){
			application=container.getInstance(applicationClass);
			if (startCallback!=null){
				startCallback.accept(application);
			}
		}
	}
	
	@Override
	public void stop(){
		if (stopCallback!=null){
			stopCallback.accept(application);
		}
	}
}
