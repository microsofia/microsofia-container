package microsofia.container.module.endpoint.msofiarmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.ClientConfig;

/**
 * Client configuration of the microsofia-rmi endpoint.
 * */
@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class MSofiaRMIClientConfig extends ClientConfig{
	@XmlElement
	private String remoteHost;
	@XmlElement
	private int remotePort;
	
	public MSofiaRMIClientConfig(){
	}

	/**
	 * Returns the remote host of the server.
	 * */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Sets the remote host of the server.
	 * */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/**
	 * Returns the remote port of the server.
	 * */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * Sets the remote port of the server.
	 * */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}	
}
