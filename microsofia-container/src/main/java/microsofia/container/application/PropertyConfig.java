package microsofia.container.application;

import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import microsofia.container.module.ResourceConfig;

@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyConfig extends ResourceConfig{
	@XmlAttribute
	private String value;
	
	public PropertyConfig(){
	}

	public PropertyConfig(String n,String v){
		name=n;
		value=v;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString(){
		return super.toString()+"[Value="+value+"]";
	}

	public static Properties toPoperties(List<PropertyConfig> cs){
		Properties p=new Properties();
		cs.forEach(it->{
			p.put(it.getName(), it.getValue());
		});
		return p;
	}
}
