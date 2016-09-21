package microsofia.container.module.endpoint.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.ClientConfig;

@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestClientConfig extends ClientConfig{
	@XmlElement
	private String url;

	public RestClientConfig(){
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
