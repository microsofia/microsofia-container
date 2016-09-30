package microsofia.container.module.endpoint.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

@XmlRootElement(name="endpoint.rest")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestEndpointConfig extends EndpointConfig<RestClientConfig,RestServerConfig>{
	@XmlElement(name="client")
	private RestClientConfig clientConfig;
	@XmlElement(name="server")
	private RestServerConfig serverConfig;

	public RestEndpointConfig(){
	}

	public RestClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(RestClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public RestServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(RestServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}
