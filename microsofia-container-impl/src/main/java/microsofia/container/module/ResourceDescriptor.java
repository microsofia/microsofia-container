package microsofia.container.module;

/**
 * Parent class for resource's descriptor.
 * */
public class ResourceDescriptor {
	//name of the resource
	protected String name;
	//is the resource required
	protected boolean req;

	protected ResourceDescriptor(String name){
		this.setName(name);
	}

	/**
	 * Returns the name of the resource.
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the resource, builder style
	 * */
	public ResourceDescriptor name(String name) {
		setName(name);
		return this;
	}
	
	/**
	 * Sets the name of the resource.
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Is the resource required?
	 * */
	public boolean isRequired() {
		return req;
	}

	/**
	 * Sets if the resource is required, builder style
	 * */
	public ResourceDescriptor required(boolean r) {
		setRequired(r);
		return this;
	}
	
	/**
	 * Sets if the resource is required.
	 * */
	public void setRequired(boolean required) {
		this.req = required;
	}

	/**
	 * Returns a string representation of the resource descriptor.
	 * */
	public String toString(){
		return "[Name:"+name+"]";
	}
}
