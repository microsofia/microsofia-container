package microsofia.container.module.db.jdbc;

import com.zaxxer.hikari.HikariConfig;

public class Utils {

	public static HikariConfig createHikariConfig(JDBCConfig jdbcConfig){
		HikariConfig config=new HikariConfig();
		if (jdbcConfig.getConnectionTimeout()!=null){
			config.setConnectionTimeout(jdbcConfig.getConnectionTimeout());
		}
		if (jdbcConfig.getValidationTimeout()!=null){
			config.setValidationTimeout(jdbcConfig.getValidationTimeout());
		}
		if (jdbcConfig.getIdleTimeout()!=null){
			config.setIdleTimeout(jdbcConfig.getIdleTimeout());
		}
		if (jdbcConfig.getLeakDetectionThreshold()!=null){
			config.setLeakDetectionThreshold(jdbcConfig.getLeakDetectionThreshold());
		}
		if (jdbcConfig.getMaxLifetime()!=null){
			config.setMaxLifetime(jdbcConfig.getMaxLifetime());
		}
		if (jdbcConfig.getMaxPoolSize()!=null){
			config.setMaximumPoolSize(jdbcConfig.getMaxPoolSize());
		}
		if (jdbcConfig.getMinIdle()!=null){
			config.setMinimumIdle(jdbcConfig.getMinIdle());
		}
		if (jdbcConfig.getCatalog()!=null){
			config.setCatalog(jdbcConfig.getCatalog());
		}
		if (jdbcConfig.getConnectionInitSql()!=null){
			config.setConnectionInitSql(jdbcConfig.getConnectionInitSql());
		}
		if (jdbcConfig.getConnectionTestQuery()!=null){
			config.setConnectionTestQuery(jdbcConfig.getConnectionTestQuery());
		}
		if (jdbcConfig.getDataSourceClassName()!=null){
			config.setDataSourceClassName(jdbcConfig.getDataSourceClassName());
		}
		if (jdbcConfig.getDataSourceJndiName()!=null){
			config.setDataSourceJNDI(jdbcConfig.getDataSourceJndiName());
		}
		if (jdbcConfig.getDriverClassName()!=null){
			config.setDriverClassName(jdbcConfig.getDriverClassName());
		}
		if (jdbcConfig.getJdbcUrl()!=null){
			config.setJdbcUrl(jdbcConfig.getJdbcUrl());
		}
		if (jdbcConfig.getPassword()!=null){
			config.setPassword(jdbcConfig.getPassword());//TODO crypt 2 ways
		}
		if (jdbcConfig.getPoolName()!=null){
			config.setPoolName(jdbcConfig.getPoolName());
		}
		if (jdbcConfig.getTransactionIsolationName()!=null){
			config.setTransactionIsolation(jdbcConfig.getTransactionIsolationName());
		}
		if (jdbcConfig.getUsername()!=null){
			config.setUsername(jdbcConfig.getUsername());
		}
		if (jdbcConfig.isAutoCommit()!=null){
			config.setAutoCommit(jdbcConfig.isAutoCommit());
		}
		if (jdbcConfig.isReadOnly()!=null){
			config.setReadOnly(jdbcConfig.isReadOnly());
		}
		if (jdbcConfig.isInitializationFailFast()!=null){
			config.setInitializationFailFast(jdbcConfig.isInitializationFailFast());
		}
		if (jdbcConfig.isIsolateInternalQueries()!=null){
			config.setIsolateInternalQueries(jdbcConfig.isIsolateInternalQueries());
		}
		if (jdbcConfig.isRegisterMbeans()!=null){
			config.setRegisterMbeans(jdbcConfig.isRegisterMbeans());
		}
		if (jdbcConfig.isAllowPoolSuspension()!=null){
			config.setAllowPoolSuspension(jdbcConfig.isAllowPoolSuspension());
		}
		if (jdbcConfig.getDataSourceProperties()!=null){
			config.setDataSourceProperties(jdbcConfig.getDataSourceProperties());
		}
		return config;
	}
}
