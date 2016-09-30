package microsofia.container.module.db.jdbc;

import java.util.List;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.ResourceBasedModule;

/*
 * - TODO : inject XADatasource
 * -TODO :define predefined database type
 */
public class JDBCModule extends ResourceBasedModule<JDBCImpl, JDBCConfig,DataSource, JDBCDescriptor, JDBCsDescriptor> implements IJDBCModule{
	
	public JDBCModule(){
		super(DataSource.class);
	}
	
	@Override
	protected DataSource createResource(String name, JDBCConfig c) {
		HikariConfig hikariConfig=Utils.createHikariConfig(c);
		return new HikariDataSource(hikariConfig);
	}
	
	@Override
	protected void stop(DataSource resource){		
		HikariDataSource hikariDataSource=(HikariDataSource)resource;
		hikariDataSource.close();
	}

	@Override
	protected JDBCImpl createResourceAnnotation(String name) {
		return new JDBCImpl(name);
	}
	
	@Override
	protected JDBCsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getJDBCsDescriptor();
	}
	
	@Override
	protected List<JDBCConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getJDBCConfigs();
	}

	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuiceJDBCModule(context);
	}	
	
	@Override
	public DataSource getDataSource(String name) {
		return getResource(name);
	}
	
	protected class GuiceJDBCModule extends GuiceModule{

		protected GuiceJDBCModule(InitializationContext context){
			super(context);
		}
		
		@Override
		protected void configure(){			
			super.configure();
			bind(IJDBCModule.class).toInstance(JDBCModule.this);			
		}
	}
}