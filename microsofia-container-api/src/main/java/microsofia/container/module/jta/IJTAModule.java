package microsofia.container.module.jta;

import javax.transaction.TransactionManager;

/**
 * Public interface of the JTA module. It can be injected in some advanced use cases. <br />
 * <br />
 * Example: <br />
 * <pre>
<br />
public class Sample{<br />
 ...
&#64;Inject
IJTAModule jtaModule;   

... 

}
 * </pre>
 */
public interface IJTAModule {

	/**
	 * Returns the JTA transaction manager.
	 * 
	 * */
	public TransactionManager getTransactionManager(String name);
}
