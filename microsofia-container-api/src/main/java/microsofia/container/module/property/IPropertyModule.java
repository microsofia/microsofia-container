package microsofia.container.module.property;

/**
 * Public interface of the property module. It can be injected using the Property annotation in case
 * there is a use case where injecting the value is not enough.
 * */
public interface IPropertyModule {

	/**
	 * Returns the value of a property. The type is a String.
	 * 
	 * @param the property name
	 * @return the string value
	 * */
	public String getStringProperty(String name);

	/**
	 * Returns the value of a property. The type is a JAXB complex object.
	 * 
	 * @param the property name
	 * @return the JAXB complex value
	 * */
	public Object getObjectProperty(String name);
	
	/**
	 * Replaces in the given string all occurenes of ${key_name} by the value of the key_name.
	 * The key_name value is provided at startup in the command line arguments with the following form 
	 * -property:key_name=value
	 * 
	 * @param the property name
	 * @return the string value
	 * */
	public String replaceProperties(String str);
}
