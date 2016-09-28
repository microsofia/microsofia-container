package microsofia.container.module.endpoint.msofiarmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.module.endpoint.ClientConfig;

@XmlRootElement(name="client")
@XmlAccessorType(XmlAccessType.FIELD)
public class MSofiaRMIClientConfig extends ClientConfig{
	@XmlElement
	private String remoteHost;
	@XmlElement
	private int remotePort;
	
	public MSofiaRMIClientConfig(){
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}	
}
