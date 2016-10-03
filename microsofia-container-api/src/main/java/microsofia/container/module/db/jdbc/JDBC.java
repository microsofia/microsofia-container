package microsofia.container.module.db.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.sql.DataSource;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject a JDBC resource, which is of type
 * {@link DataSource} <br />
 * <br />
 * <br />
 * Examples:<br />
 * <br />
 * <code> 
<pre>
public class Sample{ <br />
&#64;Inject<br />
<span class="tags">@JDBC("database_name")</span>  //the database name to connect to<br />
DataSource dataSource;  <br />
}  <br />
</pre> 
</code> 

 * The JDBC module loads from the application configuration the
 * needed informations in order to create the datasource to inject.
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface JDBC {
	
	/**
	 * The name of the database.
	 * */
	String value();
}
