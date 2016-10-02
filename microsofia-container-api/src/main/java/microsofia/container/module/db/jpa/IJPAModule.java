package microsofia.container.module.db.jpa;

import javax.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

/**
 * Public interface of the JPA module. It can be injected in case there is
 * a use case where injecting an {@link EntityManagerFactory} or a {@link SessionFactory} is not enough. <br />
 * <br />
 * Example: <br />
 * <pre>
<br />
public class Sample{<br />
 ...
&#64;Inject
IJPAModule jpaModule;   

... 

}
 * </pre>
 */
public interface IJPAModule {

	/**
	 * Returns the EntityManagerFactory that is described by the persistent-unit with the provided name.
	 * */
	public EntityManagerFactory getEntityManagerFactory(String name);

	/**
	 * Returns the SessionFactory that is described by the persistent-unit with the provided name.
	 * */
	public SessionFactory getSessionFactory(String name);
}
