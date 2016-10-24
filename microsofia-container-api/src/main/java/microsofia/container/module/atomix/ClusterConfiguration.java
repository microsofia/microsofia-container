package microsofia.container.module.atomix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.atomix.resource.Resource;

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface ClusterConfiguration {
	
	Class<? extends IAtomixConfigurator>[] configurator() default {};

	Class<? extends Resource<?>>[] resources() default {};
}
