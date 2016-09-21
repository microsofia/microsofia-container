package microsofia.container.module.endpoint.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

@XmlRootElement(name="endpoint.rest")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestEndpointConfig extends EndpointConfig<RestClientConfig,RestServerConfig>{

	public RestEndpointConfig(){
	}
}
