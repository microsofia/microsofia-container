package microsofia.container.application;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

import microsofia.container.module.db.jdbc.JDBCConfig;
import microsofia.container.module.db.jpa.JPAConfig;
import microsofia.container.module.endpoint.EndpointConfig;

@XmlRootElement(name="application")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationConfig {
	@XmlAttribute
	private String name;
	@XmlAttribute
	private String type;
	@XmlElementWrapper(name="properties")
	@XmlElement(name="property")
	private List<PropertyConfig> properties;
	@XmlElementWrapper(name="databases")
	@XmlElement(name="database")
	private List<JDBCConfig> jdbcConfigs;
	@XmlElementWrapper(name="persistence")
	@XmlElement(name="persistence-unit")
	private List<JPAConfig> jpaConfigs;
	@XmlElementWrapper(name="endpoints")
	@XmlElementRef
	private List<EndpointConfig> endpointConfigs;
	
	
	public ApplicationConfig(){
		properties=new ArrayList<>();
		jdbcConfigs=new ArrayList<>();
		jpaConfigs=new ArrayList<>();
		endpointConfigs=new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PropertyConfig> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyConfig> properties) {
		this.properties = properties;
	}
	
	public List<JDBCConfig> getJDBCConfigs() {
		return jdbcConfigs;
	}

	public void setJDBCConfigs(List<JDBCConfig> jdbcConfigs) {
		this.jdbcConfigs = jdbcConfigs;
	}

	public List<JPAConfig> getJPAConfigs() {
		return jpaConfigs;
	}

	public void setJPAConfigs(List<JPAConfig> jpaConfigs) {
		this.jpaConfigs = jpaConfigs;
	}

	public List<EndpointConfig> getEndpointConfigs() {
		return endpointConfigs;
	}

	public void setEndpointConfigs(List<EndpointConfig> c) {
		this.endpointConfigs = c;
	}

	public String toString(){
		return "Application[Name="+name+"][Type="+type+"][Properties="+properties+"][JDBCConfigs="+jdbcConfigs+"]";
	}
	
	public void writeTo(OutputStream out) throws Exception{
		Marshaller marshaller=jaxbContext.createMarshaller();
		marshaller.marshal(this, out);
	}	
	
	private static JAXBContext jaxbContext=null;
	static{
		try{
			jaxbContext=JAXBContext.newInstance(ApplicationConfig.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ApplicationConfig readFrom(InputStream in) throws Exception{
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		return (ApplicationConfig)unmarshaller.unmarshal(in);
	}
	
	public static ApplicationConfig[] readFrom(Element[] element) throws Exception{
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		List<ApplicationConfig> apps=new ArrayList<ApplicationConfig>();
		for (int i=0;i<element.length;i++){
			ApplicationConfig ac=(ApplicationConfig)unmarshaller.unmarshal(element[i]);
			if (ac!=null){
				apps.add(ac);
			}
		}
		
		return apps.toArray(new ApplicationConfig[0]);
	}
}
