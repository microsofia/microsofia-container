package microsofia.container.module.endpoint;

import java.util.HashSet;
import java.util.Set;

import microsofia.container.module.ResourceDescriptor;

public class EndpointDescriptor extends ResourceDescriptor{
	protected Set<Class<?>> clientInterfaces;
	
	public EndpointDescriptor(String name){
		super(name);
		clientInterfaces=new HashSet<>();
	}

	public Set<Class<?>> getClientInterfaces(){
		return clientInterfaces;
	}

	public void addClientInterface(Class<?> interf){
		clientInterfaces.add(interf);
	}

	public String toString(){
		return "[TYPE:Endpoint]"+super.toString();
	}
}
