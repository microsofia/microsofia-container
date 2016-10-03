package microsofia.container.module.endpoint;

import java.util.HashSet;
import java.util.Set;

import microsofia.container.module.ResourceDescriptor;

/**
 * Endpoint resource descriptor. The endpoint should know before starting all its possible client
 * interfaces that can be used for injection. This is caused by Guice limitiations ... 
 * */
public class EndpointDescriptor extends ResourceDescriptor{
	protected Set<Class<?>> clientInterfaces;
	
	public EndpointDescriptor(String name){
		super(name);
		clientInterfaces=new HashSet<>();
	}

	/**
	 * Returns all possible client interfaces of the endpoint.
	 * */
	public Set<Class<?>> getClientInterfaces(){
		return clientInterfaces;
	}

	/**
	 * Adds a new class as a possible client interface.
	 * */
	public void addClientInterface(Class<?> interf){
		clientInterfaces.add(interf);
	}

	public String toString(){
		return "[TYPE:Endpoint]"+super.toString();
	}
}
