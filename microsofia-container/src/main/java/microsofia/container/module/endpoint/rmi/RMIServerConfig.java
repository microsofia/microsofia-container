package microsofia.container.module.endpoint.rmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.ServerConfig;

@XmlRootElement(name="server")
@XmlAccessorType(XmlAccessType.FIELD)
public class RMIServerConfig extends ServerConfig{
	@XmlElement
	private int port;

	public RMIServerConfig(){
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
