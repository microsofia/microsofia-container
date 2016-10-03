package microsofia.container.module.endpoint.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

/**
 * REST endpoint configuration. <br/>
 * Example:
 <pre>
	&ltsettings>
	   ...
	
	   &ltapplication name="test" type="testapp">
			...
			&ltendpoints>
				&ltendpoint.rest name="rest1">
					&ltclient>
						&lturl>http://localhost:8080&lt/url>
					&lt/client>
					&ltserver>
						&ltport>8080&lt/port>
					&lt/server>
				&lt/endpoint.rest>
			&lt/endpoints>
	   &lt/application>
	&lt/settings>
 
 </pre>
 * 
 * */
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
