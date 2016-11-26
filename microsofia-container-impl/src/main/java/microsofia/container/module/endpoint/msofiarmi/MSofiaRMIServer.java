package microsofia.container.module.endpoint.msofiarmi;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import microsofia.container.ClassUtils;
import microsofia.container.module.endpoint.AbstractServer;
import microsofia.container.module.endpoint.Disconnect;
import microsofia.container.module.endpoint.EndpointException;
import microsofia.rmi.IClientInterestListener;
import microsofia.rmi.Server;
import microsofia.rmi.ServerAddress;
import microsofia.rmi.ServerBuilder;

/**
 * Server endpoint using microsofia-rmi server.
 * */
public class MSofiaRMIServer extends AbstractServer<MSofiaRMIServerConfig>{
	private static final Log log=LogFactory.getLog(MSofiaRMIServer.class);
	//microsofia-rmi server
	private Server server;

	public MSofiaRMIServer(MSofiaRMIServerConfig c){
		super(c);
	}
	
	/**
	 * Returns the created local server.
	 * */
	public Server getLocalServer(){
		return server;
	}
	
	/**
	 * Starts locally the microsofia-rmi server.
	 * */
	@Override
	protected void internalStart() {
		try{
			ServerBuilder serverBuilder=new ServerBuilder();
			serverBuilder.classLoader(MSofiaRMIServer.class.getClassLoader());
			serverBuilder.configuration(serverConfig.createServerConfiguration());
			serverBuilder.host(serverConfig.getHost()).port(serverConfig.getPort());
			
			//client interest listener to notify exported objects
			//that clients died/stopped
			serverBuilder.interestListener(new IClientInterestListener() {
				
				@Override
				public void removeInterest(ServerAddress remoteServerAddress, String[] ids) {
					for (String id : ids){
						try{
							//find the exported object for every id
							Object o=server.getRegistry().getObject(id);
							if (o!=null){
								//look for a method annotated with @Disconnect
								Method m=ClassUtils.getMethod(o,Disconnect.class);
								if (m!=null){
									m.invoke(o);
								}
							}
						}catch(Throwable th){
							log.error(th, th);
						}
					}
				}
				
				@Override
				public void addInterest(ServerAddress remoteServerAddress, String id) {
					//do nothing
				}
			});

			server=serverBuilder.build();
			server.start();
		} catch(Throwable th) {
			throw new EndpointException(th.getMessage(),th);
		}
	}
	
	/**
	 * Exporting an object.
	 * */
	@Override
	protected void internalExport(String id,Object object,Class<?>[] interfaces) {
		server.export(id, object, interfaces);
	}

	/**
	 * Unexporting the object.
	 * */
	@Override
	protected void internalUnexport(String id,Object object) {
		server.unexport(object);
	}
	
	/**
	 * Stoping the microsofia-rmi server.
	 * */
	@Override
	protected void internalClose() {
		try{
			server.stop();
		} catch(Throwable th) {
			throw new EndpointException(th.getMessage(),th);
		}
	}
}
