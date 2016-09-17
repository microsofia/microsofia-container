package microsofia.container.module;

import com.google.inject.Injector;
import microsofia.container.LauncherContext;

/*TODO
 * -inject JMS
 * -inject service: REST, RMI, RMI ++ (netty)
 * -inject ...
 * */
public interface IModule {

	public void preInit(LauncherContext context);

	public void postInit(Injector injector);
}
