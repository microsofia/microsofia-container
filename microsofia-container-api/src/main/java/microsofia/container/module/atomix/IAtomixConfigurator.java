package microsofia.container.module.atomix;

import io.atomix.Atomix;

public interface IAtomixConfigurator {

	public void configureAtomix(Atomix atomix);
}
