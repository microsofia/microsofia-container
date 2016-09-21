package microsofia.container.module;

import java.util.HashMap;
import java.util.Map;

public class ResourceModuleDescriptor<R extends ResourceDescriptor> {
	private Map<String,R> descriptors;
	
	protected ResourceModuleDescriptor() {
		setDescriptor(new HashMap<String,R>());
	}

	public void addDescriptor(R d){
		descriptors.put(d.getName(), d);
	}
	
	public R getDescriptor(String name) {
		return descriptors.get(name);
	}
	
	public Map<String,R> getDescriptor() {
		return descriptors;
	}

	public void setDescriptor(Map<String,R> descriptors) {
		this.descriptors = descriptors;
	}
}
