package microsofia.container.application;

import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;

import org.w3c.dom.Element;

import microsofia.container.module.ResourceConfig;

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
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public String toString(){
		return super.toString()+"[Value="+value+"]";
	}

	public static Properties toPoperties(List<PropertyConfig> cs){
		Properties p=new Properties();
		cs.forEach(it->{
			p.put(it.getName(), it.getValue());//we suppose here that all properties are string values
		});
		return p;
	}
}
