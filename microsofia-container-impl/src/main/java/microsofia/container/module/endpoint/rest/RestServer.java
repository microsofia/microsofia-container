package microsofia.container.module.endpoint.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.EndpointException;

/**
 * REST server endpoint implementation. The implementation uses Jetty and JBoss Resteasy.
 * */
public class RestServer extends AbstractServer<RestServerConfig>{
	private static Log log=LogFactory.getLog(RestServer.class);
	//jetty server
	protected Server server;
	//resteasy servlet
	protected HttpServletDispatcher dispatcher;

	public RestServer(RestServerConfig c){
		super(c);
	}
	
	/**
	 * Starts the Jetty server and register a Resteasy servlet.
	 * */
	@Override
	protected void internalStart() {
		dispatcher=new HttpServletDispatcher();
		ServletHolder servletHolder = new ServletHolder(dispatcher);
		ServletContextHandler servletCtxHandler = new ServletContextHandler();
		servletCtxHandler.addServlet(servletHolder, "/");

		server = new Server(serverConfig.getPort());
		serverConfig.getPropertyConfigs().forEach(it->{
			server.setAttribute(it.getName(), it.getValue());
		});

		server.setHandler(servletCtxHandler);
		try {
			server.start();
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}			
	}

	/**
	 * Exports a REST object. Id and interfaces are useless in that case.
	 * */
	@Override
	protected void internalExport(String id,Object object,Class<?>[] interfaces) {
		dispatcher.getDispatcher().getRegistry().addSingletonResource(object);
	}

	/**
	 * Unexports the REST object. Id is useless in that case.
	 * */
	@Override
	protected void internalUnexport(String id,Object object) {
		dispatcher.getDispatcher().getRegistry().removeRegistrations(object.getClass());//remove endpoint by class
	}
	
	/**
	 * Stops the Jetty server.
	 * */
	@Override
	protected void internalClose() {
		try{
			server.stop();
		}catch(Throwable th){
			log.debug(th,th);
		}
	}
}
