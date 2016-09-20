package microsofia.container.module.endpoint.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import microsofia.container.module.endpoint.AbstractClient;

public class RestClient extends AbstractClient{
	protected ResteasyClient client;
	protected ResteasyWebTarget target;

	public RestClient(){
	}
	
	@Override
	protected void internalStart() {
		ResteasyClientBuilder builder = new ResteasyClientBuilder();
        builder.connectionPoolSize(Integer.MAX_VALUE);
		client= builder.build();
		target = client.target("http://localhost:9998/");//TODO param all
	}
	
	@Override
	protected Object internalLookup(String id, Class<?> interf) {
		return target.proxy(interf);
	}

	@Override
	protected void internalClose() {
		client.close();
	}
}
