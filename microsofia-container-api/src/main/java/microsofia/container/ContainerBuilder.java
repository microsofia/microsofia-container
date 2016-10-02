package microsofia.container;

import java.util.Iterator;
import java.util.ServiceLoader;

import microsofia.container.application.ApplicationConfig;

/**
 * Builder of a container.
 * */
public class ContainerBuilder {
	private String[] args;
	private ApplicationConfig config;

	public ContainerBuilder(){
	}

	/**
	 * Returns the cmd line arguments.
	 * */
	public String[] getArguments() {
		return args;
	}

	/**
	 * Sets the cmd line arguments.
	 * */
	public ContainerBuilder arguments(String[] arguments) {
		this.args = arguments;
		return this;
	}

	/**
	 * Returns the application config.
	 * */
	public ApplicationConfig getApplicationConfig() {
		return config;
	}

	/**
	 * Sets the application config.
	 * */
	public ContainerBuilder applicationConfig(ApplicationConfig config) {
		this.config = config;
		return this;
	}

	/**
	 * Returns a newly created instance of container. The returned container is not started yet.
	 * */
	public Container build(){
		ServiceLoader<Container> loader=ServiceLoader.load(Container.class, ContainerBuilder.class.getClassLoader());
		Iterator<Container> it=loader.iterator();
		if (it.hasNext()){
			Container container=it.next();
			container.arguments=args;
			container.applicationConfig=config;
			return container;
		}
		throw new IllegalStateException("Couldn't load implementation of "+Container.class);
	}
}
