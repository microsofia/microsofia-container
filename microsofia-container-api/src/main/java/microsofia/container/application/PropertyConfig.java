package microsofia.container.application;

import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;

import org.w3c.dom.Element;

import microsofia.container.module.ResourceConfig;

/**
 * Represents a named property with a value. The value can be either a simple value either a marshalled JAXB object when possible.
 * <br/>
 * Example: <br/>
 <pre>

	&lt!-- a property with a simple value -->
	&ltproperty name="k6" value="1234${key6}5678${key6}232323${key6}23232${key5}${key4}"/>

	&lt!-- a property with a complex value -->
	&ltproperty name="k7">
		&ltconfig>
			&ltf1>f1&lt/f1>
			&ltf2>f2&lt/f2>
		&lt/config>
	&lt/property>
 </pre>
 * 
 * 
 * */
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyConfig extends ResourceConfig{
	@XmlAttribute
	private String value;
	@XmlAnyElement
	private Element element;
	
	public PropertyConfig(){
	}

	public PropertyConfig(String n,String v){
		name=n;
		value=v;
	}
	
	/**
	 * Returns the simple value of the property
	 * 
	 * @return the value of the property
	 * */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the simple value of the property
	 * 
	 * @param value the simple value of the property
	 * */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the complex value of the property.
	 * 
	 * @return the complex value of the property
	 * */
	public Element getElement() {
		return element;
	}

	/**
	 * Sets the complex value of the property
	 * 
	 * @param element the complex value of the property
	 * */
	public void setElement(Element element) {
		this.element = element;
	}

	/**
	 * Returns a string representation of the property.
	 * */
	public String toString(){
		return super.toString()+"[Value="+value+"]";
	}

	/**
	 * Returns a filled Properties object. All the properties have simple values in that case.
	 * 
	 * @param cs the properties to read from
	 * @return an instance of Properties containing all the passed property names with their simple values
	 * */
	public static Properties toPoperties(List<PropertyConfig> cs){
		Properties p=new Properties();
		cs.forEach(it->{
			p.put(it.getName(), it.getValue());//we suppose here that all properties are string values
		});
		return p;
	}
}
