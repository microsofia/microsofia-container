package microsofia.container.module.endpoint.rmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import microsofia.container.module.endpoint.ServerConfig;

/**
 * RMI server endpoint configuration. The configuration is used in order to create locally an RMI registry and export
 * objects.
 * */
@XmlRootElement(name="server")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIServerConfig extends ServerConfig{
	@XmlElement
	private int port;

	public RMIServerConfig(){
	}

	/**
	 * Returns the port of the registry that will be created.
	 * */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port of the registry that will be created.
	 * */
	public void setPort(int port) {
		this.port = port;
	}
}
