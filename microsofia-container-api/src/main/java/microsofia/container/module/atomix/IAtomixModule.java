package microsofia.container.module.atomix;

import io.atomix.AtomixClient;
import io.atomix.AtomixReplica;

public interface IAtomixModule {

	public AtomixClient getAtomixClient(String name);
	
	public AtomixReplica getAtomixReplica(String name);
}
