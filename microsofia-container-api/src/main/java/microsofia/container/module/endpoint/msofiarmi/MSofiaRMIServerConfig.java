package microsofia.container.module.endpoint.msofiarmi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import microsofia.container.module.endpoint.ServerConfig;
import microsofia.rmi.ServerConfiguration;

@XmlRootElement(name="server")
@XmlAccessorType(XmlAccessType.FIELD)
public class MSofiaRMIServerConfig extends ServerConfig{
	@XmlElement
	private String host;
	@XmlElement
	private Integer port;
	@XmlElement
	private Long serverGCTimeout;
	@XmlElement
    private Long clientGCPeriod;
    @XmlElement
    private Integer clientGCExceptionThreshold;
    @XmlElement(name="clientconnections")
    private ClientConnectionsConfig clientConnectionsConfig;

	public MSofiaRMIServerConfig(){
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Long getServerGCTimeout() {
		return serverGCTimeout;
	}

	public void setServerGCTimeout(Long serverGCTimeout) {
		this.serverGCTimeout = serverGCTimeout;
	}

	public Long getClientGCPeriod() {
		return clientGCPeriod;
	}

	public void setClientGCPeriod(Long clientGCPeriod) {
		this.clientGCPeriod = clientGCPeriod;
	}

	public Integer getClientGCExceptionThreshold() {
		return clientGCExceptionThreshold;
	}

	public void setClientGCExceptionThreshold(Integer clientGCExceptionThreshold) {
		this.clientGCExceptionThreshold = clientGCExceptionThreshold;
	}
	
	public ClientConnectionsConfig getClientConnectionsConfig() {
		return clientConnectionsConfig;
	}

	public void setClientConnectionsConfig(ClientConnectionsConfig clientConnectionsConfig) {
		this.clientConnectionsConfig = clientConnectionsConfig;
	}

	public ServerConfiguration createServerConfiguration(){
		ServerConfiguration config=new ServerConfiguration();
		if (serverGCTimeout!=null){
			config.setServerGCTimeout(serverGCTimeout);
		}
		if (clientGCPeriod!=null){
			config.setClientGCPeriod(clientGCPeriod);
		}
		if (clientGCExceptionThreshold!=null){
			config.setClientGCExceptionThreshold(clientGCExceptionThreshold);
		}
		if (clientConnectionsConfig!=null){
			clientConnectionsConfig.setGenericObjectPoolConfig(config.getClientConnectionsConfig());
		}
	    return config;
	}

	@XmlRootElement(name="clientconnections")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ClientConnectionsConfig{	
		@XmlElement
		private Integer maxTotal;
		@XmlElement
		private Integer maxIdle;
		@XmlElement
		private Integer minIdle;
		@XmlElement		
		private Boolean lifo;
		@XmlElement
	    private Boolean fairness;
		@XmlElement
	    private Long maxWaitMillis;
		@XmlElement
	    private Long minEvictableIdleTimeMillis;
		@XmlElement
	    private Long softMinEvictableIdleTimeMillis;
		@XmlElement
	    private Integer numTestsPerEvictionRun;
		@XmlElement
	    private String evictionPolicyClassName;
		@XmlElement
	    private Boolean testOnCreate;
		@XmlElement
	    private Boolean testOnBorrow;
		@XmlElement
	    private Boolean testOnReturn;
		@XmlElement
	    private Boolean testWhileIdle;
		@XmlElement
	    private Long timeBetweenEvictionRunsMillis;
		@XmlElement
	    private Boolean blockWhenExhausted;
		@XmlElement
	    private Boolean jmxEnabled;
		@XmlElement
	    private String jmxNamePrefix;
		@XmlElement
	    private String jmxNameBase;

    	public ClientConnectionsConfig(){
    	}
    	
    	public Integer getMaxTotal() {
			return maxTotal;
		}

		public void setMaxTotal(Integer maxTotal) {
			this.maxTotal = maxTotal;
		}

		public Integer getMaxIdle() {
			return maxIdle;
		}

		public void setMaxIdle(Integer maxIdle) {
			this.maxIdle = maxIdle;
		}

		public Integer getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(Integer minIdle) {
			this.minIdle = minIdle;
		}

		public Boolean getLifo() {
			return lifo;
		}

		public void setLifo(Boolean lifo) {
			this.lifo = lifo;
		}

		public Boolean getFairness() {
			return fairness;
		}

		public void setFairness(Boolean fairness) {
			this.fairness = fairness;
		}

		public Long getMaxWaitMillis() {
			return maxWaitMillis;
		}

		public void setMaxWaitMillis(Long maxWaitMillis) {
			this.maxWaitMillis = maxWaitMillis;
		}

		public Long getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}

		public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}

		public Long getSoftMinEvictableIdleTimeMillis() {
			return softMinEvictableIdleTimeMillis;
		}

		public void setSoftMinEvictableIdleTimeMillis(Long softMinEvictableIdleTimeMillis) {
			this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
		}

		public Integer getNumTestsPerEvictionRun() {
			return numTestsPerEvictionRun;
		}

		public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
			this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		}

		public String getEvictionPolicyClassName() {
			return evictionPolicyClassName;
		}

		public void setEvictionPolicyClassName(String evictionPolicyClassName) {
			this.evictionPolicyClassName = evictionPolicyClassName;
		}

		public Boolean getTestOnCreate() {
			return testOnCreate;
		}

		public void setTestOnCreate(Boolean testOnCreate) {
			this.testOnCreate = testOnCreate;
		}

		public Boolean getTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(Boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public Boolean getTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(Boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}

		public Boolean getTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(Boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public Long getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public Boolean getBlockWhenExhausted() {
			return blockWhenExhausted;
		}

		public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
			this.blockWhenExhausted = blockWhenExhausted;
		}

		public Boolean getJmxEnabled() {
			return jmxEnabled;
		}

		public void setJmxEnabled(Boolean jmxEnabled) {
			this.jmxEnabled = jmxEnabled;
		}

		public String getJmxNamePrefix() {
			return jmxNamePrefix;
		}

		public void setJmxNamePrefix(String jmxNamePrefix) {
			this.jmxNamePrefix = jmxNamePrefix;
		}

		public String getJmxNameBase() {
			return jmxNameBase;
		}

		public void setJmxNameBase(String jmxNameBase) {
			this.jmxNameBase = jmxNameBase;
		}

		public void setGenericObjectPoolConfig(GenericObjectPoolConfig config){
    		if (getMaxTotal()!=null){
    			config.setMaxTotal(config.getMaxTotal());
    		}
    		if (getMaxIdle()!=null){
    			config.setMaxIdle(getMaxIdle());
    		}
    		if (getMinIdle()!=null){
    			config.setMinIdle(getMinIdle());
    		}
    		if (getLifo()!=null){
    			config.setLifo(getLifo());
    		}
    		if (getFairness()!=null){
    			config.setFairness(getFairness());
    		}
    		if (getMaxWaitMillis()!=null){
    			config.setMaxWaitMillis(getMaxWaitMillis());
    		}
    		if (getMinEvictableIdleTimeMillis()!=null){
    			config.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
    		}
    		if (getSoftMinEvictableIdleTimeMillis()!=null){
    			config.setSoftMinEvictableIdleTimeMillis(getSoftMinEvictableIdleTimeMillis());
    		}
    		if (getNumTestsPerEvictionRun()!=null){
    			config.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
    		}
    		if (getEvictionPolicyClassName()!=null){
    			config.setEvictionPolicyClassName(getEvictionPolicyClassName());
    		}
    		if (getTestOnCreate()!=null){
    			config.setTestOnCreate(getTestOnCreate());
    		}
    		if (getTestOnBorrow()!=null){
    			config.setTestOnBorrow(getTestOnBorrow());
    		}
    		if (getTestOnReturn()!=null){
    			config.setTestOnReturn(getTestOnReturn());
    		}
    		if (getTestWhileIdle()!=null){
    			config.setTestWhileIdle(getTestWhileIdle());
    		}
    		if (getTimeBetweenEvictionRunsMillis()!=null){
    			config.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
    		}
    		if (getBlockWhenExhausted()!=null){
    			config.setBlockWhenExhausted(getBlockWhenExhausted());
    		}
    		if (getJmxEnabled()!=null){
    			config.setJmxEnabled(getJmxEnabled());
    		}
    		if (getJmxNamePrefix()!=null){
    			config.setJmxNamePrefix(getJmxNamePrefix());
    		}
    		if (getJmxNameBase()!=null){
    			config.setJmxNameBase(getJmxNameBase());
    		}
    	}
	}
}
