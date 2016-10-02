package microsofia.container.module;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Parent class for named resource configuration.
 * */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceConfig {
	@XmlAttribute
	protected String name;

	public ResourceConfig(){
	}

	/**
	 * Returns the unique name of the resource.
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Sets the unique name of the resource.
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a String representation of the resource.
	 * */
	public String toString(){
		return "[Name="+name+"]";
	}
}
