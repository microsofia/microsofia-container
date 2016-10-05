package microsofia.container.module.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used by the endpoint module for two purposes.<br />
 * <ol>
 * <li>All remote interfaces should be annotated with @Server</li> Example:
 * <br />
 * <code> &#64;Server //a marker to indicate that this interface is a remote one to be considered by the endpoint module<br /> public interface IRemoteObject{<br /> ...<br /> }<br /> <br /> </code>
 * <li>All objects to export should be annotated with @Server</li> Example:
 * <br />
 * <code> &#64;Server("endpoint1") //the endpoint name where to export this instance<br /> public class RemoteObject implements IRemoteObject{<br /> ...<br /> }<br /> <br /> </code>
 * </ol>
 */
@BindingAnnotation
@Target({ ElementType.TYPE,ElementType.FIELD}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Server {
	String value() default "default";
}
