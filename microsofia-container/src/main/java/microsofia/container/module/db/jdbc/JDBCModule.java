package microsofia.container.module.db.jdbc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.resource.transaction.backend.jdbc.internal.JdbcResourceLocalTransactionCoordinatorBuilderImpl;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import microsofia.container.LauncherContext;
import microsofia.container.module.AbstractModule;

/*
 * TODO 
 * -inject XADatasource
 * -define predefined database type
 */
public class JDBCModule extends AbstractModule implements IJDBCModule{
	private Map<String,JDBCConfig> configs;
	private Map<String,DataSource> datasources;
	
	public JDBCModule(){
		configs=new Hashtable<>();
		datasources=new Hashtable<>();
	}

	@Override
	public void preInit(LauncherContext context){
		List<JDBCConfig> cs=context.getCurrentApplicationConfig().getJDBCConfigs();
		cs.forEach(it->{
			configs.put(it.getName(),it);
		});
		
		context.addGuiceModule(new GuiceJDBCModule());
	}

	@Override
	public DataSource getDataSource(String name) {
		DataSource dataSource=datasources.get(name);
		if (dataSource!=null){
			return dataSource;
		}
		synchronized(this){
			dataSource=datasources.get(name);
			if (dataSource!=null){
				return dataSource;
			}

			JDBCConfig jdbcConfig=configs.get(name);
			HikariConfig hikariConfig=jdbcConfig.createHikariConfig();
			dataSource=new HikariDataSource(hikariConfig);
			datasources.put(jdbcConfig.getName(), dataSource);
		}
		return dataSource;
	}
	
	protected class GuiceJDBCModule extends com.google.inject.AbstractModule{

		protected GuiceJDBCModule(){
		}
		
		@Override
		protected void configure(){			
			configs.entrySet().forEach(it->{
				bind(Key.get(DataSource.class,new JDBCImpl(it.getKey()))).toProvider(new DataSourceProvider(it.getKey()));
			});
			
		}
	}

	protected class DataSourceProvider implements Provider<DataSource>{
		private String name;
		private DataSource dataSource;
		
		DataSourceProvider(String name){
			this.name=name;
		}
		
		@Override
		public DataSource get() {
			if (dataSource!=null){
				return dataSource;
			}
			synchronized(this){
				if (dataSource!=null){
					return dataSource;
				}
				dataSource=getDataSource(name);
			}
			return dataSource;
		}
		
	}	
}
