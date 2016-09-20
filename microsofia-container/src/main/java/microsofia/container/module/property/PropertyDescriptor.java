package microsofia.container.module.property;

public class PropertyDescriptor {
	public enum TYPE{CHAR, NUMERIC};
	private String name;
	private boolean required;
	private TYPE type;
	
	public PropertyDescriptor(String name){
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

	public TYPE getType() {
		return type;
	}

	public void setNumericType() {
		this.type = TYPE.NUMERIC;
	}
	
	public void setType(TYPE type) {
		this.type = type;
	}
	
	public boolean isTypeNumeric(){
		return type==TYPE.NUMERIC;
	}
}
