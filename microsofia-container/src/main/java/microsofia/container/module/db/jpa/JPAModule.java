package microsofia.container.module.db.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;
import microsofia.container.module.db.jdbc.IJDBCModule;

public class JPAModule extends ResourceBasedModule<JPAImpl, JPAConfig,EntityManagerFactory> implements IJPAModule{
	@Inject
	private IJDBCModule jdbcModule;
	
	public JPAModule(){
		super(EntityManagerFactory.class);
	}
	
	@Override
	protected EntityManagerFactory createResource(String name, JPAConfig c) {
		List<String> managedClassNames=new ArrayList<String>();//TODO use ApplicationDescriptor
		JPAPersistenceUnitInfo unit=new JPAPersistenceUnitInfo(name, managedClassNames, PropertyConfig.toPoperties(c.getProperties()));
		unit.setNonJtaDataSource(jdbcModule.getDataSource(c.getDatabasename()));
	    //TODO implement jtaDataSource;
		
		EntityManagerFactoryBuilderImpl emfBuilder=new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(unit),null);
		return emfBuilder.build();
	}
	
	@Override
	protected void stop(EntityManagerFactory resource){	
		resource.close();
	}

	@Override
	protected JPAImpl createResourceAnnotation(String name) {
		return new JPAImpl(name);
	}
	
	@Override
	protected List<JPAConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getJPAConfigs();
	}

	@Override
	protected com.google.inject.AbstractModule createGuiceModule(LauncherContext context) {
		return new GuiceJPAModule(context);
	}
	
	public EntityManagerFactory getEntityManagerFactory(String name){
		return getResource(name);
	}
	
	public SessionFactory getSessionFactory(String name){
		EntityManagerFactory emf=getEntityManagerFactory(name);
		return emf.unwrap(SessionFactory.class);
	}
	
	protected class GuiceJPAModule extends GuiceModule{

		protected GuiceJPAModule(LauncherContext context){
			super(context);
		}
		
		@Override
		protected void configure(){			
			super.configure();
			bind(IJPAModule.class).toInstance(JPAModule.this);
			configs.entrySet().forEach(it->{
				bind(Key.get(SessionFactory.class,new JPAImpl(it.getKey()))).toProvider(new SessionFactoryProvider(it.getKey()));
			});
		}
	}

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