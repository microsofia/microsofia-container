package microsofia.container.module.atomix;

import io.atomix.Atomix;

/**
 * The Atomix configurator that will be called to configure the Atomix client or replica before connection..
 *
 * @see Cluster
 */
public interface IAtomixConfigurator {

	/**
	 * Method called to configure the Atomix client or replica.
	 * */
	public void configureAtomix(Atomix atomix);
}
