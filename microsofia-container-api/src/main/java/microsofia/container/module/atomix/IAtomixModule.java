package microsofia.container.module.atomix;

import io.atomix.AtomixClient;
import io.atomix.AtomixReplica;

/**
 * Public interface of Atomix module. It can be injected in case there is
 * a use case where injecting {@link AtomixClient} or {@link AtomixReplica} is not enough. <br />
 * <br />
 * Example: <br />
 * <pre>
<br />
public class Sample{<br />
 ...
&#64;Inject
IAtomixModule atomixModule;   

... 

}
 * </pre>
 */
public interface IAtomixModule {

	/**
	 * Returns the AtomixClient by name.
	 * 
	 * @param name the name of the AtomixClient
	 * @return the AtomixClient which has the following name
	 * */
	public AtomixClient getAtomixClient(String name);
	
	/**
	 * Returns the AtomixReplica by name.
	 * 
	 * @param name the name of the AtomixReplica
	 * @return the AtomixReplica which has the following name
	 * */
	public AtomixReplica getAtomixReplica(String name);
}
