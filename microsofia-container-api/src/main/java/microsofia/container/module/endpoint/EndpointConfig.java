package microsofia.container.module.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

import microsofia.container.module.ResourceConfig;
import microsofia.container.module.endpoint.msofiarmi.MSofiaRMIEndpointConfig;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;
import microsofia.container.module.endpoint.rmi.RMIEndpointConfig;

@XmlSeeAlso({RestEndpointConfig.class,RMIEndpointConfig.class,MSofiaRMIEndpointConfig.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EndpointConfig<C extends ClientConfig, S extends ServerConfig> extends ResourceConfig{
	
	protected EndpointConfig(){
	}

	public abstract C getClientConfig();

	public abstract S getServerConfig();
}
