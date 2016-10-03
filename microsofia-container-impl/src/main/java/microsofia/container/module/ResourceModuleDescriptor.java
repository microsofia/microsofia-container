package microsofia.container.module;

import java.util.HashMap;
import java.util.Map;

/**
 * Parent class for resource's module descriptor.
 * */
public class ResourceModuleDescriptor<R extends ResourceDescriptor> {
	//instance of resource descriptors. Map of name->resource descriptor
	private Map<String,R> descriptors;
	
	protected ResourceModuleDescriptor() {
		setDescriptor(new HashMap<String,R>());
	}

	/**
	 * Adds a resource descriptor.
	 * */
	public void addDescriptor(R d){
		descriptors.put(d.getName(), d);
	}
	
	/**
	 * Returns a resource descriptor by name.
	 * */
	public R getDescriptor(String name) {
		return descriptors.get(name);
	}
	
	/**
	 * Returns the map of resource descriptor.
	 * 
	 * @return a map, the key is the descriptor name.
	 * */
	public Map<String,R> getDescriptor() {
		return descriptors;
	}

	/**
	 * Sets the map of resource descriptor.
	 * */
	public void setDescriptor(Map<String,R> descriptors) {
		this.descriptors = descriptors;
	}
}
