package microsofia.container.module.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject a property value. A value can be either of a simple
 * type Date, String ,float, int, double, ... or a JAXB object. <br />
 * <br />
 * Examples:<br />
 * <br />
 * <br />
 * <code> public class Sample{ <br /> <br /> &#64;Inject  <br /> <span class=
 * "tags">@Property("p1")</span>  <br /> private String v1;    //value is simple of type string<br /> <br /> <br /> &#64;Inject  <br /> <span class
 * =
 * "tags">@Property("p2")</span>  <br /> private int v2;//value is simple of type int<br /> <br /> <br /> &#64;Inject   <br /> <span class
 * =
 * "tags">@Property("p3")</span> <br /> private float v3;   //value is simple of type float<br /> <br /> <br /> &#64;Inject   <br /> <span class
 * =
 * "tags">@Property("p4")</span><br /> private Config v4;//value is a complex JAXB object<br /> <br /> ...<br /> }<br /> <br /> //the complex JAXB value object<br /> &#64;XmlRootElement <br /> public class Config{ <br /> ... <br /> }  <br /> </code>
 * </pre>
 * 
 * <br />
 * <br />
 * The property module load from the application configuration all the
 * properties and their values. <br />
 * <br />
 * It also uses the command line arguments in order to do replacement within the
 * value of all occurences of ${key_name}.<br />
 * <br />
 * The value of the key_name is passed as following within the command line
 * arguments -property:key_name=value <br />
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
	String value();
}
