package microsofia.container.module.endpoint.rmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.ClientConfig;

@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIClientConfig extends ClientConfig{
	@XmlElement
	private String host;
	@XmlElement
	private int port;
	
	public RMIClientConfig(){
	}

	public String getHost() {
		return host;
	}

	public void setHost(String h) {
		this.host = h;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
