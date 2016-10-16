package microsofia.container.application;

import java.util.function.Consumer;

import javax.inject.Inject;

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
	
	public DefaultApplication(String type,Class<?> applicationClass){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.type(type);
		descriptorBuilder=new ApplicationDescriptorBuilder(applicationDescriptor);
		this.applicationClass=applicationClass;
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
	
	@Override
	public void preInit(InitializationContext context) {
		if (initialization!=null){
			initialization.accept(context);
		}
	}

	@Override
	public void run() throws Throwable{
		application=container.getInstance(applicationClass);
		if (startCallback!=null){
			startCallback.accept(application);
		}
	}
	
	@Override
	public void stop(){
		if (stopCallback!=null){
			stopCallback.accept(application);
		}
	}
}
