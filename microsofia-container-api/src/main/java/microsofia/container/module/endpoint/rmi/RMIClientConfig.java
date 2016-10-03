package microsofia.container.module.endpoint.rmi;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import microsofia.container.module.endpoint.ClientConfig;

/**
 * RMI client endpoint configuration. The configuration is used in order to locate the remote RMI registry and locate the 
 * remote objects.
 * */
@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIClientConfig extends ClientConfig{
	@XmlElement
	private String host;
	@XmlElement
	private int port;
	
	public RMIClientConfig(){
	}

	/**
	 * Returns the host of the remote server endpoint.
	 * */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host of the remote server endpoint.
	 * */
	public void setHost(String h) {
		this.host = h;
	}

	/**
	 * Returns the port of the remote server endpoint.
	 * */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port of the remote server endpoint.
	 * */
	public void setPort(int port) {
		this.port = port;
	}
}
