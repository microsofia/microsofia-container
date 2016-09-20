package microsofia.container.module.endpoint.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import microsofia.container.module.endpoint.AbstractServer;

public class RestServer extends AbstractServer{
	protected Server server;
	protected HttpServletDispatcher dispatcher;

	public RestServer(){
	}
	
	@Override
	protected void internalStart() {
		dispatcher=new HttpServletDispatcher();
		ServletHolder servletHolder = new ServletHolder(dispatcher);
		ServletContextHandler servletCtxHandler = new ServletContextHandler();
		servletCtxHandler.addServlet(servletHolder, "/");

		Server server = new Server(9998);
		server.setHandler(servletCtxHandler);
		try {
			server.start();
		} catch (Exception e) {
			// TODO 
			e.printStackTrace();
		}			
	}

	@Override
	protected void internalExport(Object object) {
		dispatcher.getDispatcher().getRegistry().addSingletonResource(object);;
	}

	@Override
	protected void internalClose() {
		server.destroy();
	}
}
