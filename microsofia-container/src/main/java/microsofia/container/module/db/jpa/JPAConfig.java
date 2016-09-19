package microsofia.container.module.db.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceConfig;

@XmlAccessorType(XmlAccessType.FIELD)
public class JPAConfig extends ResourceConfig{
	@XmlAttribute
	private String databasename;
	@XmlElementWrapper(name="properties")
	@XmlElement(name="property")
	private List<PropertyConfig> properties;

	public JPAConfig(){
		properties=new ArrayList<PropertyConfig>();
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public List<PropertyConfig> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyConfig> properties) {
		this.properties = properties;
	}
}
