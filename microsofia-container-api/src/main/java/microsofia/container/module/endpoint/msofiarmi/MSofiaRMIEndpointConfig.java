package microsofia.container.module.endpoint.msofiarmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

/**
 * Configuration of microsofia-rmi endpoint.
 * Example:
 <pre>
	&ltsettings>
	   ...
	
	   &ltapplication name="test" type="testapp">
			&ltendpoints>
				&ltendpoint.msofiarmi name="msofiarmi1">
					&ltclient>
						&ltremoteHost>localhost&lt/remoteHost>
						&ltremotePort>9998&lt/remotePort>
					&lt/client>
					&ltserver>
						&lthost>localhost&lt/host>
						&ltport>9999&lt/port>
					&lt/server>
				&lt/endpoint.msofiarmi>
				&ltendpoint.msofiarmi name="msofiarmi2">
					&ltclient>
					&lt/client>
					&ltserver>
						&lthost>localhost&lt/host>
						&ltport>9998&lt/port>
					&lt/server>
				&lt/endpoint.msofiarmi>
			&lt/endpoints>
	   &lt/application>
	&lt/settings>
 
 </pre>
 * 
 * */
@XmlRootElement(name="endpoint.msofiarmi")
@XmlAccessorType(XmlAccessType.FIELD)
public class MSofiaRMIEndpointConfig extends EndpointConfig<MSofiaRMIClientConfig,MSofiaRMIServerConfig>{
	@XmlElement(name="client")
	private MSofiaRMIClientConfig clientConfig;
	@XmlElement(name="server")
	private MSofiaRMIServerConfig serverConfig;
	
	public MSofiaRMIEndpointConfig(){
	}

	/**
	 * Returns the client configuration.
	 * */
	public MSofiaRMIClientConfig getClientConfig() {
		return clientConfig;
	}

	/**
	 * Sets the client configuration.
	 * */
	public void setClientConfig(MSofiaRMIClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	/**
	 * Returns the server configuration.
	 * */
	public MSofiaRMIServerConfig getServerConfig() {
		return serverConfig;
	}

	/**
	 * Sets the server configuration.
	 * */
	public void setServerConfig(MSofiaRMIServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}
