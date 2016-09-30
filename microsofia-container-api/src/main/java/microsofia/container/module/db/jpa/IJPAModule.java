package microsofia.container.module.db.jpa;

import javax.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

public interface IJPAModule {

	public EntityManagerFactory getEntityManagerFactory(String name);

	public SessionFactory getSessionFactory(String name);
}
