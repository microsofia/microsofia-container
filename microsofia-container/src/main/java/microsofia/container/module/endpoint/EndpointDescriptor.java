package microsofia.container.module.endpoint;

import java.util.HashSet;
import java.util.Set;

public class EndpointDescriptor {
	protected String name;
	protected Set<Class<?>> clientInterfaces;
	
	public EndpointDescriptor(String name){
		this.name=name;
		clientInterfaces=new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Class<?>> getClientInterfaces(){
		return clientInterfaces;
	}

	public void addClientInterface(Class<?> interf){
		clientInterfaces.add(interf);
	}
}
