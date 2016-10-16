package microsofia.container.module.endpoint;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import microsofia.container.ClassUtils;
import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.ResourceBasedModule;

/**
 * The endpoint module is a resource based module that creates and manages Endpoint(s) used either to locate remote objects
 * when playing the client side of the endpoint or to un/export objects when playing the server side of the endpoint. <br/>
 * An endpoint can be a client to another endpoint or a server to another endpoint or both in the same time.
 * */
public class EndpointModule extends ResourceBasedModule<ClientImpl, EndpointConfig,Endpoint,EndpointDescriptor,EndpointsDescriptor> implements IEndpointModule{

	public EndpointModule() {
		super(Endpoint.class);
	}
	
	/**
	 * Returns the configuration of the endpoint module.
	 * */
	@Override
	protected List<EndpointConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getEndpointConfigs();
	}
	
	/**
	 * Returns the descriptor of the endpoint module.
	 * */
	@Override
	protected EndpointsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getEndpointsDescriptor();
	}

	/**
	 * Creates a new endpoint resource from its name and configuration.
	 * */
	@Override
	protected Endpoint createResource(String name, EndpointConfig c) {
		return EndpointFactory.createEndpoint(c);
	}
	
	/**
	 * Stops an endpoint.
	 * */
	@Override
	protected void stop(Endpoint resource){	
		resource.close();
	}

	/**
	 * Creates a new endpoint client resource annotation.
	 * */
	@Override
	protected ClientImpl createResourceAnnotation(String name) {
		return new ClientImpl(name);
	}

	/**
	 * Creates the endpoint Guice module.
	 * */
	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context){
		return new GuiceEndpointModule(context);
	}
	
	/**
	 * Returns an endpoint by name.
	 * */
	public Endpoint<?> getEndpoint(String name){
		return getResource(name);
	}
	
	/**
	 * Returns the client endpoint by name.
	 * */
	@Override
	public IClient getClient(String endpointName){
		return getEndpoint(endpointName).getClient();
	}
	
	/**
	 * Returns the server endpoint by name.
	 * */
	@Override
	public IServer getServer(String endpointName){
		return getEndpoint(endpointName).getServer();
	}

	/**
	 * Endpoint Guice module. It adds the following features to the default Guice module: <br/>
	 * 
	 * <li>it binds itself to its public interface IEndpointModule</li>
	 * <li>it binds all client interfaces with the @Client annotation to the ClientProvider</li>
	 * <li>intercepts and listens to Guice module in order to implement the correct behavior for @Export and @Unexport annotations</li>
	 * */
	protected class GuiceEndpointModule extends GuiceModule{

		protected GuiceEndpointModule(InitializationContext context){
			super(context);
		}
		
		@Override
		protected void configure(){
			super.configure();

			//binding current module to a public interface
			bind(IEndpointModule.class).toInstance(EndpointModule.this);
			
			//binding all configured endpoints with declared client interfaces
			for (EndpointConfig config : configs.values()){
				Endpoint endpoint=getEndpoint(config.getName());
				
				EndpointDescriptor sd=context.getCurrentApplicationDescriptor().getEndpointsDescriptor().getDescriptor(config.getName());
				if (sd==null){
					throw new EndpointException("No endpoint definition found for configuration "+config.getName());
				}
				
				bind(Key.get(IClient.class,new ClientImpl(config.getName()))).toInstance(endpoint.getClient());
				if (endpoint.getConfig().getClientConfig()!=null){
					bind(Key.get(ClientConfig.class,new ClientImpl(config.getName()))).toInstance(endpoint.getConfig().getClientConfig());
				}
				
				bind(Key.get(IServer.class,new ServerImpl(config.getName()))).toInstance(endpoint.getServer());
				if (endpoint.getConfig().getServerConfig()!=null){
					bind(Key.get(ServerConfig.class,new ServerImpl(config.getName()))).toInstance(endpoint.getConfig().getServerConfig());
				}

				for (Class<?> c : sd.getClientInterfaces()){
					bind(Key.get((Class<Object>)c,new ClientImpl(config.getName()))).toProvider(new ClientProvider(config.getName(),c));
				}
			};
			
			//listen to Guice module
			bindListener(new AbstractMatcher<TypeLiteral<?>>() {
							public boolean matches(TypeLiteral<?> typeLiteral) {
								//we are interested in all classes having @Server annotation
								return typeLiteral.getRawType().isAnnotationPresent(Server.class);
						    }

						}, new TypeListener() {				
							    @Override
							    public <I> void hear(final TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
							        typeEncounter.register(new InjectionListener<I>() {
							            @Override
							            public void afterInjection(Object i) {
							            	//when injection is done. Does it have an export method on the class?
							                Export export=ClassUtils.getAnnotationOnClass(i.getClass(),Export.class);
							                if (export!=null){
							                	//if it does, then it should be eagerly exported
								            	Server server=ClassUtils.getAnnotationOnClass(i.getClass(),Server.class);
							                	Endpoint endpoint=getEndpoint(server.value());
							                	Class<?>[] interf=ClassUtils.getInterfacesWithAnnotation(i.getClass(), Server.class);
												endpoint.getServer().export(getServerId(i,interf),i,interf);
							                }
							            }
							        });
							    }
			      		 });
			

			//intercept the classes in order to catch the call to methods annotated with @Export or @Unexport
			bindInterceptor(Matchers.annotatedWith(Server.class), Matchers.annotatedWith(Export.class).or(Matchers.annotatedWith(Unexport.class)), new MethodInterceptor() {

				public Object invoke(MethodInvocation invocation) throws Throwable {
					//method intercepted, check if its class has a @Server annotation
					Server server=ClassUtils.getAnnotationOnClass(invocation.getThis().getClass(), Server.class);
					if (server==null){
						//if the class doesn't have a @Server annotation, then it is an error, we can't know which 
						//endpoint it is associated to
						throw new EndpointException("Annotation "+Server.class+" couldnt be read from class "+invocation.getThis().getClass().getSuperclass());
					}
					
					//fetch the endpoint
					Endpoint endpoint=getEndpoint(server.value());
					Object result=invocation.proceed();

					//intefaces annotated with @Server
					Class<?>[] interf=ClassUtils.getInterfacesWithAnnotation(invocation.getThis().getClass(), Server.class);
					
					//is it an export or unexport?
					if (invocation.getMethod().isAnnotationPresent(Export.class)){
						//it is an export						
						endpoint.getServer().export(getServerId(invocation.getThis(),interf), invocation.getThis(),interf);

					}else{
						//it is an unexport
						endpoint.getServer().unexport(getServerId(invocation.getThis(),interf),invocation.getThis());
					}
					return result;
				}
			});
		}
	}
	
	/**
	 * Returns the object id from the object to export or unexport.
	 * It checks if there is an @Id annotation on one of its interfaces.
	 * */
	protected String getServerId(Object object,Class<?>[] interf){
		Id id=ClassUtils.getAnnotationOnInterface(object.getClass(), Id.class);
		if (id!=null){
			return id.value();
		}
		if (interf.length>0){
			return interf[0].getName();//return the class name of the first interface annotated with @Server ...
		}else{
			return null;
		}
	}

	/**
	 * Provider that provides a remote object given the endpoint name and the
	 * class type.
	 * */
	protected class ClientProvider implements Provider<Object>{
		private String name;
		private String id;
		private Class<?> interf;
		private Object client;
		
		ClientProvider(String name,Class<?> interf){
			this.name=name;
			this.interf=interf;
			Id aid=ClassUtils.getAnnotationOnInterface(interf, Id.class);
			if (aid!=null){
				id=aid.value();
			}else{
				id=interf.getName();
			}
		}
		
		@Override
		public Object get() {
			if (client!=null){
				return client;
			}
			synchronized(this){
				if (client!=null){
					return client;
				}
				client=getEndpoint(name).getClient().lookup(id,interf);
			}
			return client;
		}
	}
}
