package microsofia.container.module.endpoint;

public interface IServer {

	public void export(String id,Object object);

	public void unexport(String id,Object object);
}
