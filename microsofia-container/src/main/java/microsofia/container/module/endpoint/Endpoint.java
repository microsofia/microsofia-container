package microsofia.container.module.endpoint;

public abstract class Endpoint<C extends EndpointConfig> {
	protected C config;
	protected AbstractClient client;
	protected AbstractServer server;

	protected Endpoint(C config){
		this.config=config;
	}

	public C getConfig(){
		return config;
	}
	
	public IClient getClient(){
		return client;
	}

	public IServer getServer(){
		return server;
	}

	public void close(){
		client.close();
		server.close();
	}
}
