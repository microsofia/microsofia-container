package microsofia.container.module.atomix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.atomix.resource.Resource;

/**
 * Annotation used to configure the Atomix cluster.
 *
 * @see Cluster
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface ClusterConfiguration {
	
	/**
	 * Returns a configurator that will be called on the Atomix client or replica before connection.
	 * */
	Class<? extends IAtomixConfigurator>[] configurator() default {};

	/**
	 * Returns a list of Atomix resources that will be added to the Atomix client or replica.
	 * */
	Class<? extends Resource<?>>[] resources() default {};
}
