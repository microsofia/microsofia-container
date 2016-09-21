package microsofia.container.module;

public class ResourceDescriptor {
	protected String name;
	protected boolean required;

	protected ResourceDescriptor(String name){
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String toString(){
		return "[Name:"+name+"]";
	}
}
