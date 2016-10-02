package microsofia.container.module.endpoint;

/**
 * The client public API of an endpoint.
 * */
public interface IClient {

	/**
	 * Locates a remote object.
	 * 
	 * @param id the object id
	 * @param interf the remote interface of the object to locate
	 * @return a proxy of the remote object
	 * */
	public Object lookup(String id,Class<?> interf);
}
