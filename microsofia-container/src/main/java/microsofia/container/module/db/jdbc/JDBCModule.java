package microsofia.container.module.db.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import microsofia.container.LauncherContext;
import microsofia.container.module.ResourceBasedModule;

/*
 * TODO 
 * -inject XADatasource
 * -define predefined database type
 */
public class JDBCModule extends ResourceBasedModule<JDBCImpl, JDBCConfig,DataSource> implements IJDBCModule{
	
	public JDBCModule(){
		super(DataSource.class);
	}

	@Override
	protected DataSource createResource(String name, JDBCConfig c) {
		HikariConfig hikariConfig=c.createHikariConfig();
		return new HikariDataSource(hikariConfig);
	}

	@Override
	protected JDBCImpl createResourceAnnotation(String name) {
		return new JDBCImpl(name);
	}
	
	@Override
	protected List<JDBCConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getJDBCConfigs();
	}

	@Override
	protected com.google.inject.AbstractModule createGuiceModule() {
		return new GuiceJDBCModule();
	}	
	
	@Override
	public DataSource getDataSource(String name) {
		return getResource(name);
	}
	
	protected class GuiceJDBCModule extends GuiceModule{

		protected GuiceJDBCModule(){
		}
		
		@Override
		protected void configure(){			
			super.configure();
			bind(IJDBCModule.class).toInstance(JDBCModule.this);			
		}
	}
}
