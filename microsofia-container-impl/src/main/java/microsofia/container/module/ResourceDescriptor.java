package microsofia.container.module;

/**
 * Parent class for resource's descriptor.
 * */
public class ResourceDescriptor {
	//name of the descriptor
	protected String name;
	//is the resource required
	protected boolean required;

	protected ResourceDescriptor(String name){
		this.setName(name);
	}

	/**
	 * Returns the name of the resource descriptor.
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the resource descriptor.
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Is the resource required?
	 * */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Sets if the resource is required.
	 * */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Returns a string representation of the resource descriptor.
	 * */
	public String toString(){
		return "[Name:"+name+"]";
	}
}
