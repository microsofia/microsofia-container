package microsofia.container.module.property;

import microsofia.container.module.ResourceDescriptor;

public class PropertyDescriptor extends ResourceDescriptor{
	public enum TYPE{CHAR, NUMERIC, OBJECT};
	private TYPE type;
	private Class<?> objectClass;
	
	public PropertyDescriptor(String name){
		super(name);
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
	
	public boolean isTypeObject(){
		return type==TYPE.OBJECT;
	}

	public Class<?> getObjectClass() {
		return objectClass;
	}
	
	public void setObjectType(Class<?> objectClass) {
		this.type = TYPE.OBJECT;
		this.objectClass=objectClass;
	}
	
	public String toString(){
		return "[TYPE:Property]"+super.toString();
	}
}
