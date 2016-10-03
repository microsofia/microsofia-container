package microsofia.container.module.db.jdbc;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import microsofia.container.module.ResourceConfig;

/**
 * JDBC resource configuration containing needed informations to connect to a
 * database. <br />
 * As the implementation is based on HikariCP JDBC connection pool, most of the
 * following configuration can be found here
 * http://github.com/brettwooldridge/HikariCP/wiki/Configuration
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JDBCConfig extends ResourceConfig{
	/**
	 * The Database type. Instead of configuring the dataSourceClassName filling the type is enough.
	 * */
	public enum TYPE{
		derby("org.apache.derby.jdbc.ClientDataSource"),
		firebird("org.firebirdsql.pool.FBSimpleDataSource"),
		h2("org.h2.jdbcx.JdbcDataSource"),
		hsqldb("org.hsqldb.jdbc.JDBCDataSource"),
		db2("com.ibm.db2.jcc.DB2SimpleDataSource"),
		informix("com.informix.jdbcx.IfxDataSource"),
		sqlserver("com.microsoft.sqlserver.jdbc.SQLServerDataSource"),
		mysql("com.mysql.jdbc.jdbc2.optional.MysqlDataSource"),
		mariadb("org.mariadb.jdbc.MySQLDataSource"),
		oracle("oracle.jdbc.pool.OracleDataSource"),
		orientdb("com.orientechnologies.orient.jdbc.OrientDataSource"),
		postgresql("com.impossibl.postgres.jdbc.PGDataSource"),
		maxdb("com.sap.dbtech.jdbc.DriverSapDB"),
		sqlite("org.sqlite.SQLiteDataSource"),
		sybase("com.sybase.jdbc4.jdbc.SybDataSource");
		
		private String dataSourceClass;
		
		TYPE(String c){
			dataSourceClass=c;
		}
		
		public String getDataSourceClass(){
			return dataSourceClass;
		}		
	};
	@XmlAttribute
	private TYPE type;
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
	
	public TYPE getType(){
		return type;
	}
	
	public void setType(TYPE t){
		this.type=t;
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
}
