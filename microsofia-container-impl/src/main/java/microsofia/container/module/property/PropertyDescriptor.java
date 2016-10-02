package microsofia.container.module.property;

import microsofia.container.module.ResourceDescriptor;

/**
 * The resource property descriptor.
 * */
public class PropertyDescriptor extends ResourceDescriptor{
	/**
	 * The type of the property.
	 * */
	public enum TYPE{
		/**
		 * The type is a string.
		 * */
		CHAR, 
		/**
		 * The type is numeric: float, int, long, double, short, ...
		 * */
		NUMERIC, 
		/**
		 * The type is a complex JAXB object.
		 * */
		OBJECT
	};
	private TYPE type;
	//class of the jaxb object
	private Class<?> objectClass;
	
	public PropertyDescriptor(String name){
		super(name);
	}

	/**
	 * Returns the type of the property.
	 * */
	public TYPE getType() {
		return type;
	}

	/**
	 * Sets the type as numeric.
	 * */
	public void setNumericType() {
		this.type = TYPE.NUMERIC;
	}
	
	/**
	 * Sets the type of the property.
	 * */
	public void setType(TYPE type) {
		this.type = type;
	}

	/**
	 * Is the type numeric?
	 * */
	public boolean isTypeNumeric(){
		return type==TYPE.NUMERIC;
	}
	
	/**
	 * Is the type a complex JAXB object?
	 * */
	public boolean isTypeObject(){
		return type==TYPE.OBJECT;
	}

	/**
	 * Returns the class of the complex JAXB object.
	 * */
	public Class<?> getObjectClass() {
		return objectClass;
	}
	
	/**
	 * Sets the type as complex JAXB object and sets its class.
	 * 
	 * */
	public void setObjectType(Class<?> objectClass) {
		this.type = TYPE.OBJECT;
		this.objectClass=objectClass;
	}

	/**
	 * Returns a string representation of the property descriptor.
	 * */
	public String toString(){
		return "[TYPE:Property]"+super.toString();
	}
}
