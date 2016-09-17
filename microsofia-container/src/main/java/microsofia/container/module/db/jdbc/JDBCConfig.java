package microsofia.container.module.db.jdbc;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.zaxxer.hikari.HikariConfig;

@XmlAccessorType(XmlAccessType.FIELD)
public class JDBCConfig {
	@XmlAttribute
	private String name;
	@XmlElement
	private Long connectionTimeout;
	@XmlElement   
	private Long validationTimeout;
	@XmlElement   
	private Long idleTimeout;
	@XmlElement
	private Long leakDetectionThreshold;
	@XmlElement   
	private Long maxLifetime;
	@XmlElement   
	private Integer maxPoolSize;
	@XmlElement   
	private Integer minIdle;
	@XmlElement
	private String catalog;
	@XmlElement   
	private String connectionInitSql;
	@XmlElement   
	private String connectionTestQuery;
	@XmlElement   
	private String dataSourceClassName;
	@XmlElement   
	private String dataSourceJndiName;
	@XmlElement   
	private String driverClassName;
	@XmlElement   
	private String jdbcUrl;
	@XmlElement   
	private String password;
	@XmlElement   
	private String poolName;
	@XmlElement   
	private String transactionIsolationName;
	@XmlElement   
	private String username;
	@XmlElement
	private Boolean isAutoCommit;
	@XmlElement   
	private Boolean isReadOnly;
	@XmlElement   
	private Boolean isInitializationFailFast;
	@XmlElement   
	private Boolean isIsolateInternalQueries;
	@XmlElement   
	private Boolean isRegisterMbeans;
	@XmlElement   
	private Boolean isAllowPoolSuspension;
	@XmlElement
	private Properties dataSourceProperties;
	
	public JDBCConfig(){
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Long getValidationTimeout() {
		return validationTimeout;
	}

	public void setValidationTimeout(Long validationTimeout) {
		this.validationTimeout = validationTimeout;
	}

	public Long getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(Long idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public Long getLeakDetectionThreshold() {
		return leakDetectionThreshold;
	}

	public void setLeakDetectionThreshold(Long leakDetectionThreshold) {
		this.leakDetectionThreshold = leakDetectionThreshold;
	}

	public Long getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(Long maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getConnectionInitSql() {
		return connectionInitSql;
	}

	public void setConnectionInitSql(String connectionInitSql) {
		this.connectionInitSql = connectionInitSql;
	}

	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}

	public void setConnectionTestQuery(String connectionTestQuery) {
		this.connectionTestQuery = connectionTestQuery;
	}

	public String getDataSourceClassName() {
		return dataSourceClassName;
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	public String getDataSourceJndiName() {
		return dataSourceJndiName;
	}

	public void setDataSourceJndiName(String dataSourceJndiName) {
		this.dataSourceJndiName = dataSourceJndiName;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getTransactionIsolationName() {
		return transactionIsolationName;
	}

	public void setTransactionIsolationName(String transactionIsolationName) {
		this.transactionIsolationName = transactionIsolationName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean isAutoCommit() {
		return isAutoCommit;
	}

	public void setAutoCommit(Boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
	}

	public Boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(Boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public Boolean isInitializationFailFast() {
		return isInitializationFailFast;
	}

	public void setInitializationFailFast(Boolean isInitializationFailFast) {
		this.isInitializationFailFast = isInitializationFailFast;
	}

	public Boolean isIsolateInternalQueries() {
		return isIsolateInternalQueries;
	}

	public void setIsolateInternalQueries(Boolean isIsolateInternalQueries) {
		this.isIsolateInternalQueries = isIsolateInternalQueries;
	}

	public Boolean isRegisterMbeans() {
		return isRegisterMbeans;
	}

	public void setRegisterMbeans(Boolean isRegisterMbeans) {
		this.isRegisterMbeans = isRegisterMbeans;
	}

	public Boolean isAllowPoolSuspension() {
		return isAllowPoolSuspension;
	}

	public void setAllowPoolSuspension(Boolean isAllowPoolSuspension) {
		this.isAllowPoolSuspension = isAllowPoolSuspension;
	}

	public Properties getDataSourceProperties() {
		return dataSourceProperties;
	}

	public void setDataSourceProperties(Properties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

	public HikariConfig createHikariConfig(){
		HikariConfig config=new HikariConfig();
		if (connectionTimeout!=null){
			config.setConnectionTimeout(connectionTimeout);
		}
		if (validationTimeout!=null){
			config.setValidationTimeout(validationTimeout);
		}
		if (idleTimeout!=null){
			config.setIdleTimeout(idleTimeout);
		}
		if (leakDetectionThreshold!=null){
			config.setLeakDetectionThreshold(leakDetectionThreshold);
		}
		if (maxLifetime!=null){
			config.setMaxLifetime(maxLifetime);
		}
		if (maxPoolSize!=null){
			config.setMaximumPoolSize(maxPoolSize);
		}
		if (minIdle!=null){
			config.setMinimumIdle(minIdle);
		}
		if (catalog!=null){
			config.setCatalog(catalog);
		}
		if (connectionInitSql!=null){
			config.setConnectionInitSql(connectionInitSql);
		}
		if (connectionTestQuery!=null){
			config.setConnectionTestQuery(connectionTestQuery);
		}
		if (dataSourceClassName!=null){
			config.setDataSourceClassName(dataSourceClassName);
		}
		if (dataSourceJndiName!=null){
			config.setDataSourceJNDI(dataSourceJndiName);
		}
		if (driverClassName!=null){
			config.setDriverClassName(driverClassName);
		}
		if (jdbcUrl!=null){
			config.setJdbcUrl(jdbcUrl);
		}
		if (password!=null){
			config.setPassword(password);//TODO crypt 2 ways
		}
		if (poolName!=null){
			config.setPoolName(poolName);
		}
		if (transactionIsolationName!=null){
			config.setTransactionIsolation(transactionIsolationName);
		}
		if (username!=null){
			config.setUsername(username);
		}
		if (isAutoCommit!=null){
			config.setAutoCommit(isAutoCommit);
		}
		if (isReadOnly!=null){
			config.setReadOnly(isReadOnly);
		}
		if (isInitializationFailFast!=null){
			config.setInitializationFailFast(isInitializationFailFast);
		}
		if (isIsolateInternalQueries!=null){
			config.setIsolateInternalQueries(isIsolateInternalQueries);
		}
		if (isRegisterMbeans!=null){
			config.setRegisterMbeans(isRegisterMbeans);
		}
		if (isAllowPoolSuspension!=null){
			config.setAllowPoolSuspension(isAllowPoolSuspension);
		}
		if (dataSourceProperties!=null){
			config.setDataSourceProperties(dataSourceProperties);
		}
		return config;
	}
}
