package microsofia.container.module.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by the endpoint module to indicate the id of an object when
 * exporting it or when locating it.<br />
 * <br />
 * Example:<br />
 * <code> 
 * &#64;Server <br />
   &#64;Id("id_object") //id used when locating the object<br />  
   public interface IRemoteObject{<br /> 
   ...<br /> 
   }<br />
	<br />  
	&#64;Server <br />
	@Id("id_object") //id used when exporting the object<br /> 
	public class RemoteObject implements IRemoteObject{<br /> 
	... <br />  
	}<br />  
	<br />  
	</code>
 * </ol>
 */
@Target({ ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
	String value();
}
