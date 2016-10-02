package microsofia.container;

import org.w3c.dom.Element;

import microsofia.container.application.ApplicationConfig;

/**
 * A microsofia container. When starting the container, it will load its modules and application, initialize them and 
 * run the application. The modules offer several capabilities and are configured via the application configuration.
 * 
 * */
public abstract class Container {
	protected String[] arguments;
	protected ApplicationConfig applicationConfig;
 	
	public Container(){
	}

	/**
	 * The cmd line arguments used to configure the container.
	 * 
	 * @return arguments used to configure the container
	 * */
	public String[] getArguments() {
		return arguments;
	}

	/**
	 * Returns the application configuration of the container.
	 * 
	 * @return the configuration of the container 
	 * */
	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	/**
	 * Starts the container. It will load all its modules and application, initialize them and then
	 * run the application.
	 * 
	 * */
	public abstract void start() throws Throwable;

	/**
	 * Stops the container and frees any resources it is currently using.
	 * 
	 * */
	public abstract void stop();

	/**
	 * Main that runs the container. 
	 * 
	 * @param argv the command line arguments
	 * @param element array of Element(s). First element of the array should contain the application configuration in an XML marshalled form.
	 * */
	public static void main(String[] argv, Element[] element) throws Throwable{
		ContainerBuilder builder=new ContainerBuilder().arguments(argv);
		if (element!=null){
			ApplicationConfig[] apps=ApplicationConfig.readFrom(element);
			if (apps==null || apps.length==0){
				throw new IllegalStateException("No application is configured to start. Please check your settings file.");
			}
			builder.applicationConfig(apps[0]);//for the moment we just launch one
		}
		Container container=builder.build();
		container.start();
	}
}
