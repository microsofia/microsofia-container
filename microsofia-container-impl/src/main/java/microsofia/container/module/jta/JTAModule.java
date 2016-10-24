package microsofia.container.module.jta;

import java.util.List;

import javax.transaction.TransactionManager;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.ResourceBasedModule;

/**
 * The JTA module creates and manages a TransactionManager. 
 * */
public class JTAModule extends ResourceBasedModule<JTAImpl, JTAConfig,TransactionManager, JTADescriptor, JTAsDescriptor> implements IJTAModule{
	
	public JTAModule(){
		super(TransactionManager.class,JTAConfig.class);
	}

	/**
	 * Create a TransactionManager from its name and config. 
	 * */
	@Override
	protected TransactionManager createResource(String name, JTAConfig c) {
		return TransactionManagerServices.getTransactionManager();//LATER: when implemeting XA, allow TM configuration
	}
	
	/**
	 * Closes the TM.
	 * */
	@Override
	protected void stop(TransactionManager tm){		
		BitronixTransactionManager btm=(BitronixTransactionManager)tm;
		btm.shutdown();
	}

	/**
	 * Returns an implementation of the JTA annotation.
	 * */
	@Override
	protected JTAImpl createResourceAnnotation(String name) {
		return new JTAImpl(name);
	}

	/**
	 * Returns the JTA module descriptor from the application descriptor.
	 * */
	@Override
	protected JTAsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getJTAsDescriptor();
	}
	
	/**
	 * Returns the JTA module configurations from the application configuration.
	 * */
	@Override
	protected List<JTAConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getJTAConfigs();
	}

	/**
	 * Creates a JTA Guice module.
	 * */
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuiceJDBCModule(context);
	}	
	
	/**
	 * Returns the TM resource by name.
	 * */
	@Override
	public TransactionManager getTransactionManager(String name) {
		return getResource(name);
	}
	
	/**
	 * The JTA Guice module.
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
			bind(IJTAModule.class).toInstance(JTAModule.this);			
		}
	}
}
