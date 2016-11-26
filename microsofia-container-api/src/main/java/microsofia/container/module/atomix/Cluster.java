package microsofia.container.module.atomix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.google.inject.BindingAnnotation;

/**
 * Annotation used to inject an Atomix resource, which is of type
 * {@link AtomixClient} or {@link AtomixReplica} <br />
 * <br />
 * <br />
 * Examples:<br />
 * <br />
 * <code> 
<pre>
public class Sample{ <br />

&#64;Inject<br />
<span class="tags">@ClusterConfiguration(configurator=MyConfigurator.class)</span>  //dynamic configuration of the cluster <br />
<span class="tags">@Cluster("cluster_name")</span>  //the cluster name to connect to<br />
AtomixClient atomixClient;  <br />
}  <br />
</pre> 
</code> 

 *
 */
@BindingAnnotation
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface Cluster {

	String value();
}
