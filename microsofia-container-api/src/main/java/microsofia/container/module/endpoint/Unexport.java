package microsofia.container.module.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by the endpoint module to unexport an object instance.<br />
 * <br/>
 * Example:<br />
 * <code> 
 * &#64;Server <br /> 
 * public interface IRemoteObject{<br /> 
 * ...<br /> 
 * }<br /> 
 * <br /> 
 * 
 * &#64;Server <br /> 
 * public class RemoteObject implements IRemoteObject{<br /> 
 * ...<br /> 
 * <br/>
 * &#64;Export //the object will be exported once this method is called <br/>
 * public void afterInitialization(){ <br/>
 *  ... <br/>
 * } <br/>
 * 
 * <br/>
 * 
 * &#64;Unexport //the object will be unexported once this method is called <br/>
 * public void shutDown(){ <br/>
 *  ... <br/>
 * } <br/>
 * 
 * }<br /> 
 * 
 * <br /> 
 *
 * </code>
 * </ol>
 */
@Target({ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Unexport {
}
