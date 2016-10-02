package microsofia.container.module.db.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject a JPA resource, which is of type
 * {@link EntityManagerFactory} or {@link SessionFactory} <br />
 * <br />
 * Examples:<br />
 * <br />
 * <code> <pre> public class Sample{ <br />
<br /> &#64;Inject<br /> <span class=
"tags">@JPA("persitent-unit_name")</span>  <br /> EntityManagerFactory entityManagerFactory;<br />
  <br /> }  <br /> </pre> </code> The JPA module loads from the application
 * configuration the needed informations in order to create the resources to
 * inject.
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface JPA {
	String value();
}
