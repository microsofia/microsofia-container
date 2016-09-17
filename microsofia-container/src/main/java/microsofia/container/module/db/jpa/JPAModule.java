package microsofia.container.module.db.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import com.google.inject.Key;
import com.google.inject.Provider;

import microsofia.container.LauncherContext;
import microsofia.container.module.AbstractModule;
import microsofia.container.module.db.jdbc.IJDBCModule;
import microsofia.container.module.property.PropertyConfig;

public class JPAModule extends AbstractModule{
	private Map<String,JPAConfig> jpaConfigs;
	private Map<String,EntityManagerFactory> emfs;
	private IJDBCModule jdbcModule;
	
	public JPAModule(){
		jpaConfigs=new Hashtable<>();
		emfs=new Hashtable<>();
	}

	@Override
	public void preInit(LauncherContext context){
		jdbcModule=context.getCoreModule(IJDBCModule.class);

		List<JPAConfig> cs=context.getCurrentApplicationConfig().getJPAConfigs();
		cs.forEach(it->{
			jpaConfigs.put(it.getName(), it);
		});

		context.addGuiceModule(new GuiceJPAModule());
	}

	public EntityManagerFactory getEntityManagerFactory(String name){
		EntityManagerFactory emf=emfs.get(name);
		if (emf!=null){
			return emf;
		}
		synchronized(this){
			emf=emfs.get(name);
			if (emf!=null){
				return emf;
			}
			JPAConfig config=jpaConfigs.get(name);
			List<String> managedClassNames=new ArrayList<String>();//TODO how to get the classes to load
			JPAPersistenceUnitInfo unit=new JPAPersistenceUnitInfo(name, managedClassNames, PropertyConfig.toPoperties(config.getProperties()));
			unit.setNonJtaDataSource(jdbcModule.getDataSource(config.getDatabasename()));
		    //TODO implement jtaDataSource;
			
			EntityManagerFactoryBuilderImpl emfBuilder=new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(unit),null);
			emf= emfBuilder.build();

			emfs.put(name, emf);
		}
		return emf;
	}
	
	public SessionFactory getSessionFactory(String name){
		EntityManagerFactory emf=getEntityManagerFactory(name);
		return emf.unwrap(SessionFactory.class);
	}
	
	protected class GuiceJPAModule extends com.google.inject.AbstractModule{

		protected GuiceJPAModule(){
		}
		
		@Override
		protected void configure(){			
			jpaConfigs.entrySet().forEach(it->{
				bind(Key.get(EntityManagerFactory.class,new JPAImpl(it.getKey()))).toProvider(new EntityManagerFactoryProvider(it.getKey()));
				bind(Key.get(SessionFactory.class,new JPAImpl(it.getKey()))).toProvider(new SessionFactoryProvider(it.getKey()));
			});
			
		}
	}

	protected class EntityManagerFactoryProvider implements Provider<EntityManagerFactory>{
		private String name;
		private EntityManagerFactory emf;
		
		EntityManagerFactoryProvider(String name){
			this.name=name;
		}
		
		@Override
		public EntityManagerFactory get() {
			if (emf!=null){
				return emf;
			}
			synchronized(this){
				if (emf!=null){
					return emf;
				}
				emf=getEntityManagerFactory(name);
			}
			return emf;
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