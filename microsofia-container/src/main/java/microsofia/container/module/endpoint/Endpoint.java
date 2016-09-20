package microsofia.container.module.endpoint;

import java.util.Hashtable;
import java.util.Map;

public abstract class Endpoint<C extends EndpointConfig> {
	protected C config;
	protected AbstractClient client;
	protected AbstractServer server;
	protected Map<String,Class<?>> ids;

	protected Endpoint(C config){
		this.config=config;
		ids=new Hashtable<>();
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
