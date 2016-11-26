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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

import microsofia.container.module.atomix.AtomixConfig;
import microsofia.container.module.db.jdbc.JDBCConfig;
import microsofia.container.module.db.jpa.JPAConfig;
import microsofia.container.module.endpoint.EndpointConfig;
import microsofia.container.module.jta.JTAConfig;

/**
 * Application configuration used to configure the container at startup.<br/>
 * The container has several available types of applications that are accessible via its classpath at runtime.
 * Only the application which type is specified in the configuration will be started. <br/>
 * Typically the configuration is read from the microsofia-boot settings file. <br/>
 * <br/>
 * Example:
 <pre>
	&ltsettings>
	   ...
	
	   &ltapplication name="test" type="testapp">
			&ltproperties>
				&ltproperty name="k1" value="v1"/>
				&ltproperty name="k2" value="v2"/>
				&ltproperty name="k3" value="3"/>
				&ltproperty name="k4" value="1234${key4}5678${key4}91012${key5}${key6}"/>
				&ltproperty name="k5" value="${key5}1234${key5}${key4}${key6}"/>
				&ltproperty name="k6" value="1234${key6}5678${key6}232323${key6}23232${key5}${key4}"/>
				&ltproperty name="k7">
					&ltconfig>
						&ltf1>f1&lt/f1>
						&ltf2>f2&lt/f2>
					&lt/config>
				&lt/property>
			&lt/properties>	
			&ltdatabases>
				&ltdatabase name="db1">
					&ltjdbcUrl>jdbc:derby:target/sample1;create=true&lt/jdbcUrl>
					&ltdriverClassName>org.apache.derby.jdbc.EmbeddedDriver&lt/driverClassName>
				&lt/database>
				&ltdatabase name="db2">
					&ltjdbcUrl>jdbc:derby:target/sample2;create=true&lt/jdbcUrl>
				</database>
			&lt/databases>
			&ltpersistence>
				&ltpersistence-unit name="jpa1" databasename="db1">
					&ltproperties>
						&lt!--  property name="" value=""/ -->
					&lt/properties>				
				&lt/persistence-unit>
				&ltpersistence-unit name="jpa2" databasename="db1">
					&ltproperties>
						&lt!--  property name="" value=""/ -->
					&lt/properties>				
				&lt/persistence-unit>
			&lt/persistence>
			&ltendpoints>
				&ltendpoint.rest name="rest1">
					&ltclient>
						&lturl>http://localhost:8080&lt/url>
					&lt/client>
					&ltserver>
						&ltport>8080&lt/port>
					&lt/server>
				&lt/endpoint.rest>
				&ltendpoint.rmi name="rmi1">
					&ltclient>
						&lthost>localhost&lt/host>
						&ltport>8081&lt/port>
					&lt/client>
					&ltserver>
						&ltport>8081&lt/port>
					&lt/server>
				&lt/endpoint.rmi>
				&ltendpoint.msofiarmi name="msofiarmi1">
					&ltclient>
						&ltremoteHost>localhost&lt/remoteHost>
						&ltremotePort>9998&lt/remotePort>
					&lt/client>
					&ltserver>
						&lthost>localhost&lt/host>
						&ltport>9999&lt/port>
					&lt/server>
				&lt/endpoint.msofiarmi>
				&ltendpoint.msofiarmi name="msofiarmi2">
					&ltclient>
					&lt/client>
					&ltserver>
						&lthost>localhost&lt/host>
						&ltport>9998&lt/port>
					&lt/server>
				&lt/endpoint.msofiarmi>
			&lt/endpoints>
	   &lt/application>
	&lt/settings>
 
 </pre>
 * 
 * 
 * **/
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
	@XmlElementWrapper(name="transactionmanagers")
	@XmlElement(name="transactionmanager")
	private List<JTAConfig> jtaConfigs;
	@XmlElementWrapper(name="databases")
	@XmlElement(name="database")
	private List<JDBCConfig> jdbcConfigs;
	@XmlElementWrapper(name="persistence")
	@XmlElement(name="persistence-unit")
	private List<JPAConfig> jpaConfigs;
	@XmlElementWrapper(name="endpoints")
	@XmlElementRef
	private List<EndpointConfig> endpointConfigs;
	@XmlElementWrapper(name="clusters")
	@XmlElement(name="cluster")
	private List<AtomixConfig> atomixConfigs;
	@XmlAnyElement
	private Element[] element;
	
	public ApplicationConfig(){
		properties=new ArrayList<>();
		jtaConfigs=new ArrayList<>();
		jdbcConfigs=new ArrayList<>();
		jpaConfigs=new ArrayList<>();
		endpointConfigs=new ArrayList<>();
		atomixConfigs=new ArrayList<>();
	}

	/**
	 * The name of the application instance, not to confuse with the application type.
	 * 
	 * @return the application instance name
	 * */
	public String getName() {
		return name;
	}

	/**
	 * Sets the application instance name, not to confuse with the application type
	 * 
	 * @param name the application instance name
	 * */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the application type name to instantiate and run.
	 * 
	 * @return the application type name to to instantiate and run.
	 * */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the application type name
	 * 
	 * @param type the application type name
	 * */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the properties configuration that will be used to configure the property module.
	 * 
	 * @return the properties configuration
	 * */
	public List<PropertyConfig> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties configuration that will be used to configure the property module.
	 * 
	 * @param properties the properties configuration
	 * */
	public void setProperties(List<PropertyConfig> properties) {
		this.properties = properties;
	}
	
	/**
	 * Returns the JTA module configuration.
	 * */
	public List<JTAConfig> getJTAConfigs() {
		return jtaConfigs;
	}

	/**
	 * Sets the JTA module configuration.
	 * */
	public void setJTAConfigs(List<JTAConfig> jtaConfigs) {
		this.jtaConfigs = jtaConfigs;
	}

	/**
	 * Returns the JDBC configurations that will be used to configure the JDBC module.
	 * 
	 * @return the JDBC module configurations
	 * */
	public List<JDBCConfig> getJDBCConfigs() {
		return jdbcConfigs;
	}

	/**
	 * Sets the JDBC configurations that will be used to configure the JDBC module.
	 * 
	 * @param jdbcConfigs the JDBC module configuration
	 * */
	public void setJDBCConfigs(List<JDBCConfig> jdbcConfigs) {
		this.jdbcConfigs = jdbcConfigs;
	}

	/**
	 * Returns the JPA configurations that will be used to configure the JPA module.
	 * 
	 * @return the JPA module configuration
	 * */
	public List<JPAConfig> getJPAConfigs() {
		return jpaConfigs;
	}

	/**
	 * Sets the JPA configurations that will be used to configure the JPA module.
	 * 
	 * @param jpaConfigs the JPA module configuration
	 * */
	public void setJPAConfigs(List<JPAConfig> jpaConfigs) {
		this.jpaConfigs = jpaConfigs;
	}

	/**
	 * Returns the endpoint configurations that will be used to configure the endpoint module.
	 * 
	 * @return the endpoint module configuration
	 * */
	public List<EndpointConfig> getEndpointConfigs() {
		return endpointConfigs;
	}

	/**
	 * Sets the endpoint configuration that will be used to configure the endpoint module.
	 * 
	 * @param c the endpoint module configuration
	 * */
	public void setEndpointConfigs(List<EndpointConfig> c) {
		this.endpointConfigs = c;
	}
	
	/**
	 * Returns the Atomix module configuration
	 * 
	 * @return the Atomix module configuration
	 * */
	public List<AtomixConfig> getAtomixConfigs() {
		return atomixConfigs;
	}

	/**
	 * Sets the Atomix module configuration.
	 * 
	 * @param atomixConfigs the Atomix module configuration
	 * */
	public void setAtomixConfigs(List<AtomixConfig> atomixConfigs) {
		this.atomixConfigs=atomixConfigs;
	}
	
	/**
	 * Returns the configuration that the container couldn't unmarshal. 
	 * It can allow the caller to have a custom configuration.
	 * */
	public Element[] getElement(){
		return element;
	}
	
	/**
	 * Sets a custom configuration that is not container specific. 
	 * It can allow the caller to have a custom configuration.
	 * */
	public void setElement(Element[] element){
		this.element=element;
	}

	/**
	 * String representation of the configuration.
	 * */
	public String toString(){
		return "Application[Name="+name+"][Type="+type+"][Properties="+properties+"][JDBCConfigs="+jdbcConfigs+"]";
	}
	
	/**
	 * Marshaling the configuration to an output stream.
	 */
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
	
	/**
	 * Reading the configuration from an input stream.
	 * */
	public static ApplicationConfig readFrom(InputStream in) throws Exception{
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		return (ApplicationConfig)unmarshaller.unmarshal(in);
	}
	
	/**
	 * Unmarshaling configuration instances from the array of Element. 
	 * If an item of the array is null, then no instance is added in the returned array.
	 * 
	 * @param element array of element containing instances to unmarshal
	 * @return instances of configurations
	 * */
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
