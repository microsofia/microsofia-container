package microsofia.container.module.endpoint.rmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;
import microsofia.container.module.endpoint.rest.RestClientConfig;
import microsofia.container.module.endpoint.rest.RestServerConfig;

@XmlRootElement(name="endpoint.rmi")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIEndpointConfig extends EndpointConfig{
	@XmlElement(name="client")
	private RMIClientConfig clientConfig;
	@XmlElement(name="server")
	private RMIServerConfig serverConfig;
	
	public RMIEndpointConfig(){
	}

	public RMIClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(RMIClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public RMIServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(RMIServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}
