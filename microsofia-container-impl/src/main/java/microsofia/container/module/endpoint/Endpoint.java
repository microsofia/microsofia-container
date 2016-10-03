package microsofia.container.module.endpoint;

/**
 * A endpoint resource composed of its configuration, a client endpoint and a server endpoint.
 * */
public abstract class Endpoint<C extends EndpointConfig> {
	protected C config;
	protected AbstractClient client;
	protected AbstractServer server;

	protected Endpoint(C config){
		this.config=config;
	}

	/**
	 * Returns the endpoint configuration.
	 * */
	public C getConfig(){
		return config;
	}
	
	/**
	 * Returns the client endpoint.
	 * */
	public IClient getClient(){
		return client;
	}

	/**
	 * Returns the server endpoint.
	 * */
	public IServer getServer(){
		return server;
	}

	/**
	 * Closes the client and the server endpoints.
	 * */
	public void close(){
		client.close();
		server.close();
	}
}
