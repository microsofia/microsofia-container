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

/**
 * Atomix resource configuration. The configuration can represent either a client or a server/replica<br />
 * For a replica, the localmember field has to be configured.
 */
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
	
	/**
	 * Returns the replica local member configuration.
	 * 
	 * @return the local member configuration for a replica
	 * */
	public Member getLocalMember() {
		return localMember;
	}

	/**
	 * Sets the replica local member configuration.
	 * 
	 * @param localMember the local member configuration for a replica
	 * */
	public void setLocalMember(Member localMember) {
		this.localMember = localMember;
	}

	/**
	 * Returns the other members configuration
	 * 
	 * @return the other members configuration
	 * */
	public List<Member> getMembers() {
		return members;
	}

	/**
	 * Sets the other members configuration
	 * 
	 * @param members the other members configuration
	 * */
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	/**
	 * Returns the properties used to configure the Atomix connection, for a client or a replica.
	 * 
	 * @return the Atomix properties
	 * */
	public List<PropertyConfig> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties used to configure the Atomix connection, for a client or a replica.
	 * 
	 * @param properties the Atomix properties
	 * */
	public void setProperties(List<PropertyConfig> properties) {
		this.properties = properties;
	}

	/**
	 * Represents a member configuration.
	 * */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Member{
		@XmlAttribute
		private String host;
		@XmlAttribute
		private int port;
		
		public Member(){
		}

		/**
		 * Returns the host of the member.
		 * 
		 * @return the host
		 * */
		public String getHost() {
			return host;
		}

		/**
		 * Sets the host of the member.
		 * 
		 * @param host the host
		 * */
		public void setHost(String host) {
			this.host = host;
		}

		/**
		 * Returns the port of the member.
		 * 
		 * @return the port
		 * */
		public int getPort() {
			return port;
		}

		/**
		 * Sets the port of the member.
		 * 
		 * @param port the port
		 * */
		public void setPort(int port) {
			this.port = port;
		}
	}
}
