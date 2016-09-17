package microsofia.container.module.property;

import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

//TODO move to common
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyConfig {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String value;
	
	public PropertyConfig(){
	}

	public PropertyConfig(String n,String v){
		name=n;
		value=v;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return "Name="+name+", Value="+value;
	}

	public static Properties toPoperties(List<PropertyConfig> cs){
		Properties p=new Properties();
		cs.forEach(it->{
			p.put(it.getName(), it.getValue());
		});
		return p;
	}
}
