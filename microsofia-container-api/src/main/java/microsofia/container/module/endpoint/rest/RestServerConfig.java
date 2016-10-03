package microsofia.container.module.endpoint.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.application.PropertyConfig;
import microsofia.container.module.endpoint.ServerConfig;

/**
 * REST server configuration.
 * */
@XmlRootElement(name="server")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestServerConfig extends ServerConfig{
	@XmlElement
	private int port;
	@XmlElementWrapper(name="properties")
	@XmlElement(name="property")
	private List<PropertyConfig> propertyConfigs;

	public RestServerConfig(){
		setPropertyConfigs(new ArrayList<>());
	}

	/**
	 * Returns the port of the Jetty server to start.
	 * */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port of the Jetty server to start.
	 * */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Returns the property configs of the server.
	 * */
	public List<PropertyConfig> getPropertyConfigs() {
		return propertyConfigs;
	}

	/**
	 * Sets the property configs of the server.
	 * */
	public void setPropertyConfigs(List<PropertyConfig> propertyConfigs) {
		this.propertyConfigs = propertyConfigs;
	}
}
