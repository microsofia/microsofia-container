package microsofia.container.module.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject a client endpoint.<br />
 * <br />
 * Examples:<br />
 * <code>
 * <p><span class="tags">
 * <code>@Server //all the remote interfaces should be annotated with @Server<br> 
   public interface IRemoteObject{<br />
    ...<br /> 
    }<br />
    <br />
	<br /> 
	public class Sample{ <br />
	<br /> 
	&#64;Inject<br /> 
	<span class="tags">@Client("endpoint1")</span><br /> 
	private IRemoteObject remoteObject;<br />
	<br /> 
	}
	</code><br />
 * <br />
 * </code>
 * </p>
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Client {
	String value();
}