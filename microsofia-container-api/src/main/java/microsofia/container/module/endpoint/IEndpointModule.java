package microsofia.container.module.endpoint;

/**
 * Public interface of the endpoint module. It can be injected for advanced use cases. <br />
 * <br />
 * Example: <br />
 * <pre>
<br />
public class Sample{<br />
 ...
&#64;Inject
IEndpointModule endpointModule;   

... 

}
 * </pre>
 */
public interface IEndpointModule {

	/**
	 * Returns the client public API for an endpoint.
	 * 
	 * */
	public IClient getClient(String endpointName);
	
	/**
	 * Returns the server public API for an endpoint.
	 * */
	public IServer getServer(String endpointName);
}
