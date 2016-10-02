package microsofia.container.module.endpoint;

/**
 * The server public API of an endpoint.
 * */
public interface IServer {

	/**
	 * Exports an object with its id and remote interfaces.
	 * 
	 * @param id the object id
	 * @param object the object to export
	 * @param interfaces the interfaces of the exported object
	 * */
	public void export(String id,Object object,Class<?>[] interfaces);

	/**
	 * Unexports an already exported remote object.
	 * 
	 * @param id the object id
	 * @param object the exported object
	 * */
	public void unexport(String id,Object object);
}
