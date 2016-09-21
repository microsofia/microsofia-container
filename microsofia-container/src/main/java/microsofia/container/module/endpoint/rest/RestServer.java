package microsofia.container.module.endpoint.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.EndpointException;

public class RestServer extends AbstractServer<RestServerConfig>{
	private static Log log=LogFactory.getLog(RestServer.class);
	protected Server server;
	protected HttpServletDispatcher dispatcher;

	public RestServer(RestServerConfig c){
		super(c);
	}
	
	@Override
	protected void internalStart() {
		dispatcher=new HttpServletDispatcher();
		ServletHolder servletHolder = new ServletHolder(dispatcher);
		ServletContextHandler servletCtxHandler = new ServletContextHandler();
		servletCtxHandler.addServlet(servletHolder, "/");

		server = new Server(serverConfig.getPort());//TODO more param for server
		server.setHandler(servletCtxHandler);
		try {
			server.start();
		} catch (Exception e) {
			throw new EndpointException(e.getMessage(),e);
		}			
	}

	@Override
	protected void internalExport(String id,Object object) {
		dispatcher.getDispatcher().getRegistry().addSingletonResource(object);
	}

	@Override
	protected void internalUnexport(String id,Object object) {
		dispatcher.getDispatcher().getRegistry().removeRegistrations(object.getClass());//remove endpoint by class
	}
	
	@Override
	protected void internalClose() {
		try{
			server.stop();
		}catch(Throwable th){
			log.debug(th,th);
		}
	}
}
