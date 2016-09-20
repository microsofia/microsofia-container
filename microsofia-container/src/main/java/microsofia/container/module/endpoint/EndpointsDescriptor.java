package microsofia.container.module.endpoint;

import java.util.HashMap;
import java.util.Map;

public class EndpointsDescriptor {
	private Map<String,EndpointDescriptor> descriptors;

	public EndpointsDescriptor(){
		descriptors=new HashMap<String, EndpointDescriptor>();
	}

	public Map<String,EndpointDescriptor> getDescriptors() {
		return descriptors;
	}

	public EndpointDescriptor getDescriptor(String name){
		return descriptors.get(name);
	}

	public void setDescriptors(Map<String,EndpointDescriptor> descriptors) {
		this.descriptors = descriptors;
	}
	
	public void addDescriptor(EndpointDescriptor sd){
		descriptors.put(sd.getName(),sd);
	}
}
