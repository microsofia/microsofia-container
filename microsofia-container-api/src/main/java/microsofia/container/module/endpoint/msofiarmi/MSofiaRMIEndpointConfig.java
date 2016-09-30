package microsofia.container.module.endpoint.msofiarmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

@XmlRootElement(name="endpoint.msofiarmi")
@XmlAccessorType(XmlAccessType.FIELD)
public class MSofiaRMIEndpointConfig extends EndpointConfig<MSofiaRMIClientConfig,MSofiaRMIServerConfig>{
	@XmlElement(name="client")
	private MSofiaRMIClientConfig clientConfig;
	@XmlElement(name="server")
	private MSofiaRMIServerConfig serverConfig;
	
	public MSofiaRMIEndpointConfig(){
	}

	public MSofiaRMIClientConfig getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(MSofiaRMIClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	public MSofiaRMIServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(MSofiaRMIServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}
