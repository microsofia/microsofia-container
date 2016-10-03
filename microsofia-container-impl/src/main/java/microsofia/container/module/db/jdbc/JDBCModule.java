package microsofia.container.module.db.jdbc;

import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.ResourceBasedModule;
import microsofia.container.module.jta.IJTAModule;

/**
 * The JDBC module creates and manages DataSource(s). <br/>
 * XA Datasource are not handled by the module. In theory it should be and everything is setup for that (TM, ...). <br/>
 * As by experience XA handling is costly to implement correctly, it will be postponed as much as possible. <br/>
 * Better and easier solutions can be used at higher layer.
 * */
public class JDBCModule extends ResourceBasedModule<JDBCImpl, JDBCConfig,DataSource, JDBCDescriptor, JDBCsDescriptor> implements IJDBCModule{
	@SuppressWarnings("unused")
	@Inject
	private IJTAModule jtaModule;
	
	public JDBCModule(){
		super(DataSource.class);
	}

	/**
	 * Create a DataSource from its name and config. 
	 * The created DataSource is a {@link HikariDataSource}.
	 * */
	@Override
	protected DataSource createResource(String name, JDBCConfig c) {
		HikariConfig hikariConfig=Utils.createHikariConfig(c);
		return new HikariDataSource(hikariConfig);
	}
	
	/**
	 * Closes the datasource.
	 * */
	@Override
	protected void stop(DataSource resource){		
		HikariDataSource hikariDataSource=(HikariDataSource)resource;
		hikariDataSource.close();
	}

	/**
	 * Returns an implementation of the JDBC annotation.
	 * */
	@Override
	protected JDBCImpl createResourceAnnotation(String name) {
		return new JDBCImpl(name);
	}

	/**
	 * Returns the JDBC module descriptor from the application descriptor.
	 * */
	@Override
	protected JDBCsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getJDBCsDescriptor();
	}
	
	/**
	 * Returns the JDBC module configurations from the application configuration.
	 * */
	@Override
	protected List<JDBCConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getJDBCConfigs();
	}

	/**
	 * Creates a JDBC Guice module.
	 * */
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuiceJDBCModule(context);
	}	
	
	/**
	 * Returns the datasource resource by name.
	 * */
	@Override
	public DataSource getDataSource(String name) {
		return getResource(name);
	}
	
	/**
	 * The JDBC Guice module.
	 * */
	protected class GuiceJDBCModule extends GuiceModule{

		protected GuiceJDBCModule(InitializationContext context){
			super(context);
		}

		/**
		 * The additional binding that the module adds is its public interface, binded to itself.
		 * */
		@Override
		protected void configure(){			
			super.configure();
			//binding the public interface to itself
			bind(IJDBCModule.class).toInstance(JDBCModule.this);			
		}
	}
}
