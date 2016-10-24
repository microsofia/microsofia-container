package microsofia.container.module.atomix;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceConfig;

@XmlRootElement(name="cluster")
@XmlAccessorType(XmlAccessType.FIELD)
public class AtomixConfig extends ResourceConfig{
	@XmlElement(name="localmember")
	private Member localMember;
	@XmlElementWrapper(name="members")
	@XmlElement(name="member")
	private List<Member> members;
	@XmlElementWrapper(name="properties")
	@XmlElement(name="property")
	private List<PropertyConfig> properties;
	
	public AtomixConfig(){
		properties=new ArrayList<>();
		members=new ArrayList<>();
	}
	
	public Member getLocalMember() {
		return localMember;
	}

	public void setLocalMember(Member localMember) {
		this.localMember = localMember;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	public List<PropertyConfig> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyConfig> properties) {
		this.properties = properties;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Member{
		@XmlAttribute
		private String host;
		@XmlAttribute
		private int port;
		
		public Member(){
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}
}
