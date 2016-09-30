package microsofia.container.module.endpoint.msofiarmi;

import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.EndpointException;
import microsofia.rmi.Server;
import microsofia.rmi.ServerBuilder;

public class MSofiaRMIServer extends AbstractServer<MSofiaRMIServerConfig>{
	private Server server;

	public MSofiaRMIServer(MSofiaRMIServerConfig c){
		super(c);
	}
	
	public Server getLocalServer(){
		return server;
	}
	
	@Override
	protected void internalStart() {
		try{
			ServerBuilder serverBuilder=new ServerBuilder();
			serverBuilder.classLoader(MSofiaRMIServer.class.getClassLoader());//TODO is it the good cl?
			serverBuilder.configuration(serverConfig.createServerConfiguration());
			serverBuilder.host(serverConfig.getHost()).port(serverConfig.getPort());
			//TODO interestListener
			server=serverBuilder.build();
			server.start();
		} catch(Throwable th) {
			throw new EndpointException(th.getMessage(),th);
		}
	}
	
	@Override
	protected void internalExport(String id,Object object,Class<?>[] interfaces) {
		if (id==null){
			if (interfaces==null || interfaces.length==0){
				throw new EndpointException("Exporting object "+object+" has no id and no public interfaces.");
			}
			id=interfaces[0].getName();
		}
		server.export(id, object, interfaces);
	}

	@Override
	protected void internalUnexport(String id,Object object) {
		server.unexport(object);
	}
	
	@Override
	protected void internalClose() {
		try{
			server.stop();
		} catch(Throwable th) {
			throw new EndpointException(th.getMessage(),th);
		}
	}
}
