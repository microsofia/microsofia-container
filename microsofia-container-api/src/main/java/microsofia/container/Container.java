package microsofia.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import microsofia.container.application.ApplicationConfig;

/**
 * A microsofia container. When starting the container, it will load all its modules and application, initialize them and 
 * run the application. The modules offer several capabilities and are configured via the application configuration.
 * 
 * */
public abstract class Container {
	private static Log log=LogFactory.getLog(Container.class);
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
	 * Injects dependencies into the fields and methods of the object.
	 * 
	 * */
	public abstract void injectMembers(Object object);

	/**
	 * Returns an instance of type c with dependencies injected.
	 * 
	 * */
	public abstract <T> T getInstance(Class<T> c);
	
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
		ApplicationConfig[] apps=null;
		if (element!=null){
			apps=ApplicationConfig.readFrom(element);
		}
		if (apps==null || apps.length==0){
			throw new IllegalStateException("No application is configured to start. Please check your settings file.");
		}

		Vector<Throwable> ths=new Vector<>();
		List<Thread> threads=new ArrayList<>();

		for (int i=0;i<apps.length;i++){
			ContainerBuilder builder=new ContainerBuilder().arguments(argv);
			builder.applicationConfig(apps[i]);
			Container container=builder.build();
			Thread th=new Thread(){
				public void run(){
					try {
						container.start();
					} catch (Throwable e) {
						e.printStackTrace();
						log.error(e,e);
						ths.add(e);
					}
				}
			};
			threads.add(th);
			th.start();
		}
		
		threads.forEach(it->{
			try{
				it.join();
			}catch(Exception e){
				log.error(e,e);
			}
		});
		
		if (ths.size()>0){
			throw ths.get(0);//should throw all of them
		}
	}
}
