package microsofia.container.module.db.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;
import microsofia.container.module.db.jdbc.IJDBCModule;

/**
 * The JPA module creates and manages EntityManagerFactory. <br/>
 * */
public class JPAModule extends ResourceBasedModule<JPAImpl, JPAConfig,EntityManagerFactory,JPADescriptor,JPAsDescriptor> implements IJPAModule{
	//JDBC module used to get the DataSource(s)
	@Inject
	private IJDBCModule jdbcModule;
	
	public JPAModule(){
		super(EntityManagerFactory.class);
	}
	
	/**
	 * Creates an EntityManagerFactory from its name and configuration.
	 * 
	 * @param name the name of the resource
	 * @param c the configuration of the resource
	 * @return the newly created EntityManagerFactory
	 * */
	@Override
	protected EntityManagerFactory createResource(String name, JPAConfig c) {
		JPADescriptor desc=moduleDescriptor.getDescriptor(c.getName());
		if (desc==null){
			//no resource descriptor found. We cannot create the resource
			//without the entity classes
			throw new JPAException("No jpa definition found for configuration "+c.getName());
		}
		
		JPAPersistenceUnitInfo unit=new JPAPersistenceUnitInfo(name, desc.getEntitiesClassName(), PropertyConfig.toPoperties(c.getProperties()));
		unit.setNonJtaDataSource(jdbcModule.getDataSource(c.getDatabasename()));
	    //we dont use XA as the JDBC module doesnt implement it for the moment
		
		EntityManagerFactoryBuilderImpl emfBuilder=new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(unit),null);
		return emfBuilder.build();
	}
	
	/**
	 * Closes the EntityManagerFactory.
	 * */
	@Override
	protected void stop(EntityManagerFactory resource){	
		resource.close();
	}

	/**
	 * Creates an implementation for the JPA resource annotation.
	 * */
	@Override
	protected JPAImpl createResourceAnnotation(String name) {
		return new JPAImpl(name);
	}
	
	/**
	 * Returns the JPA module descriptor.
	 * */
	@Override
	protected JPAsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getJPAsDescriptor();
	}
	
	/**
	 * Returns the JPA module configuration.
	 * */
	@Override
	protected List<JPAConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getJPAConfigs();
	}

	/**
	 * Returns the JPA Guice module.
	 * */
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuiceJPAModule(context);
	}
	
	/**
	 * Returns the EntityManagerFactory by name.
	 * */
	@Override
	public EntityManagerFactory getEntityManagerFactory(String name){
		return getResource(name);
	}
	
	/**
	 * Returns the SessionFactory by name.
	 * */
	@Override
	public SessionFactory getSessionFactory(String name){
		EntityManagerFactory emf=getEntityManagerFactory(name);
		return emf.unwrap(SessionFactory.class);
	}
	
	/**
	 * JPA Guice module. It adds the following additional bindings:<br/>
	 * <li>its public interface IJPAModule binds to itself</li>
	 * <li>for every JPA resource binds a provider with the SessionFactory as a type</li>
	 * */
	protected class GuiceJPAModule extends GuiceModule{

		protected GuiceJPAModule(InitializationContext context){
			super(context);
		}
		
		@Override
		protected void configure(){			
			super.configure();
			//binding with the public interface
			bind(IJPAModule.class).toInstance(JPAModule.this);
			
			//for every JPA resource, binds to SessionFactory type
			configs.entrySet().forEach(it->{
				bind(Key.get(SessionFactory.class,new JPAImpl(it.getKey()))).toProvider(new SessionFactoryProvider(it.getKey()));
			});
		}
	}

	/**
	 * The provider used to bind with the SessionFactory type.
	 * */
	protected class SessionFactoryProvider implements Provider<SessionFactory>{
		private String name;
		private SessionFactory sf;
		
		SessionFactoryProvider(String name){
			this.name=name;
		}
		
		@Override
		public SessionFactory get() {
			if (sf!=null){
				return sf;
			}
			synchronized(this){
				if (sf!=null){
					return sf;
				}
				sf=getSessionFactory(name);
			}
			return sf;
		}
	}
}