package microsofia.container.module.jta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.transaction.TransactionManager;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject a JTA transaction manager, which is of type
 * {@link TransactionManager} <br />
 * <br />
 * <br />
 * Examples:<br />
 * <br />
 * <code> 
<pre>
public class Sample{ <br />
&#64;Inject<br />
<span class="tags">@JTA</span> <br />
TransactionManager transactionManager;  <br />
}  <br />
</pre> 
</code> 

 * 
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface JTA {
	String value() default "";
}
