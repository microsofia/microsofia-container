package microsofia.container.module.endpoint;

public interface IEndpointModule {

	public IClient getClient(String endpointName);
	
	public IServer getServer(String endpointName);
}
