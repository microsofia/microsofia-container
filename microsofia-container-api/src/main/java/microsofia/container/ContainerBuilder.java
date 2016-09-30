package microsofia.container;

import java.util.Iterator;
import java.util.ServiceLoader;

import microsofia.container.application.ApplicationConfig;

public class ContainerBuilder {
	private String[] arguments;
	private ApplicationConfig applicationConfig;

	public ContainerBuilder(){
	}

	public String[] getArguments() {
		return arguments;
	}

	public ContainerBuilder arguments(String[] arguments) {
		this.arguments = arguments;
		return this;
	}

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public ContainerBuilder applicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
		return this;
	}

	public Container build(){
		ServiceLoader<Container> loader=ServiceLoader.load(Container.class, ContainerBuilder.class.getClassLoader());
		Iterator<Container> it=loader.iterator();
		if (it.hasNext()){
			Container container=it.next();
			container.arguments=arguments;
			container.applicationConfig=applicationConfig;
			return container;
		}
		throw new IllegalStateException("Couldn't load implementation of "+Container.class);
	}
}
