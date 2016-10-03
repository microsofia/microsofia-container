package microsofia.container.module.endpoint.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.application.PropertyConfig;
import microsofia.container.module.endpoint.ClientConfig;

/**
 * REST client endpoint configuration.
 * */
@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestClientConfig extends ClientConfig{
	@XmlElement
	private String url;
	@XmlElementWrapper(name="properties")
	@XmlElement(name="property")
	private List<PropertyConfig> propertyConfigs;

	public RestClientConfig(){
		setPropertyConfigs(new ArrayList<>());
	}

	/**
	 * Returns the server URL.
	 * */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the server URL.
	 * */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Returns the property configs of the client.
	 * */
	public List<PropertyConfig> getPropertyConfigs() {
		return propertyConfigs;
	}

	/**
	 * Sets the property configs of the client.
	 * */
	public void setPropertyConfigs(List<PropertyConfig> propertyConfigs) {
		this.propertyConfigs = propertyConfigs;
	}
}
