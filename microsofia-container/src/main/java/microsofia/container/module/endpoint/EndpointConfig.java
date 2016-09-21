package microsofia.container.module.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;

import microsofia.container.module.ResourceConfig;
import microsofia.container.module.endpoint.rest.RestClientConfig;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;
import microsofia.container.module.endpoint.rest.RestServerConfig;

@XmlSeeAlso({RestEndpointConfig.class,RestClientConfig.class,RestServerConfig.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EndpointConfig<C extends ClientConfig, S extends ServerConfig> extends ResourceConfig{
	@XmlElementRef
	private C clientConfig;
	@XmlElementRef
	private S serverConfig;
	
	protected EndpointConfig(){
	}

	public C getClientConfig() {
		return clientConfig;
	}

	public void setClientConfig(C clientConfig) {
		this.clientConfig = clientConfig;
	}

	public S getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(S serverConfig) {
		this.serverConfig = serverConfig;
	}
}
