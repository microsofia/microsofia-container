package microsofia.container.module.endpoint.rmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.EndpointConfig;

/**
 * RMI endpoint configuration.<br/>
 * Example:<br/>
  <pre>
	&ltsettings>
	   ...
	
	   &ltapplication name="test" type="testapp">
	   		...

			&ltendpoints>
				&ltendpoint.rmi name="rmi1">
					&ltclient>
						&lthost>server_hostname&lt/host>
						&ltport>8084&lt/port>
					&lt/client>
					&ltserver>
						&ltport>8081&lt/port>
					&lt/server>
				&lt/endpoint.rmi>
			&lt/endpoints>
	   &lt/application>
	&lt/settings>
 </pre>
 * 
 * 
 * */
@XmlRootElement(name="endpoint.rmi")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIEndpointConfig extends EndpointConfig<RMIClientConfig,RMIServerConfig>{
	@XmlElement(name="client")
	private RMIClientConfig clientConfig;
	@XmlElement(name="server")
	private RMIServerConfig serverConfig;
	
	public RMIEndpointConfig(){
	}

	/**
	 * Returns the RMI client configuration.
	 * */
	public RMIClientConfig getClientConfig() {
		return clientConfig;
	}

	/**
	 * Sets the RMI client configuration.
	 * */
	public void setClientConfig(RMIClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}

	/**
	 * Returns the RMI server configuration.
	 * */
	public RMIServerConfig getServerConfig() {
		return serverConfig;
	}

	/**
	 * Sets the RMI server configuration.
	 * */
	public void setServerConfig(RMIServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}
