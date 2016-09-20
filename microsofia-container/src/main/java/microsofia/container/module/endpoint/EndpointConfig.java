package microsofia.container.module.endpoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

import microsofia.container.module.ResourceConfig;
import microsofia.container.module.endpoint.rest.RestEndpointConfig;

@XmlSeeAlso({RestEndpointConfig.class})
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EndpointConfig extends ResourceConfig{

	public EndpointConfig(){
	}
}
