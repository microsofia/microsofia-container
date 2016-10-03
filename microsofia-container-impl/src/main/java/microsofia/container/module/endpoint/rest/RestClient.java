package microsofia.container.module.endpoint.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import microsofia.container.module.endpoint.AbstractClient;

/**
 * REST client endpoint implementation.
 * */
public class RestClient extends AbstractClient<RestClientConfig>{
	protected ResteasyClient client;
	protected ResteasyWebTarget target;

	public RestClient(RestClientConfig c){
		super(c);
	}
	
	/**
	 * Creates a {@link ResteasyClient} that will be used to create remote proxies.
	 * */
	@Override
	protected void internalStart() {
		ResteasyClientBuilder builder = new ResteasyClientBuilder();
        builder.connectionPoolSize(Integer.MAX_VALUE);
		client= builder.build();
		clientConfig.getPropertyConfigs().forEach(it->{
			client.property(it.getName(), it.getValue());
		});
		
		target = client.target(clientConfig.getUrl());
	}
	
	/**
	 * Returns a REST remote proxy. The id is useless in that case.
	 * */
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		return target.proxy(interf);
	}

	/**
	 * Closes the {@link ResteasyClient}
	 * */
	@Override
	protected void internalClose() {
		client.close();
	}
}
