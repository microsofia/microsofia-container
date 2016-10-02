package microsofia.container.module.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by the endpoint module to export an object instance.<br />
 * <ol>
 * <li>An object can be eagerly exported if its class has an @Export annotation.</li> 
 * Example:<br />
 * <code> 
 * &#64;Server <br /> 
 * public interface IRemoteObject{<br /> 
 * ...<br /> 
 * }<br /> 
 * <br /> 
 * 
 * &#64;Server <br /> 
 * &#64;Export //the object will be exported once instantiated and all needed resources are injected. <br /> 
 * public class RemoteObject implements IRemoteObject{<br /> 
 * ...<br /> 
 * }<br /> 
 * 
 * <br /> 
 * </code>
 * <li>An object can be exported via a call to a method that is annotated with @Export</li> <br/>
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
 * }<br /> 
 * 
 * <br /> 
 *
 * </code>
 * </ol>
 */
@Target({ElementType.METHOD,ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {
}
