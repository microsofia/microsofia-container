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

import microsofia.container.LauncherContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.ResourceBasedModule;

public class EndpointModule extends ResourceBasedModule<ClientImpl, EndpointConfig,Endpoint,EndpointDescriptor,EndpointsDescriptor> implements IEndpointModule{

	public EndpointModule() {
		super(Endpoint.class);
	}
	
	@Override
	protected List<EndpointConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getEndpointConfigs();
	}
	
	@Override
	protected EndpointsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getEndpointsDescriptor();
	}

	@Override
	protected Endpoint createResource(String name, EndpointConfig c) {
		return EndpointFactory.createEndpoint(c);
	}
	
	@Override
	protected void stop(Endpoint resource){	
		resource.close();
	}

	@Override
	protected ClientImpl createResourceAnnotation(String name) {
		return new ClientImpl(name);
	}

	@Override
	protected com.google.inject.AbstractModule createGuiceModule(LauncherContext context){
		return new GuiceEndpointModule(context);
	}
	
	public Endpoint<?> getEndpoint(String name){
		return getResource(name);
	}
	
	@Override
	public IClient getClient(String endpointName){
		return getEndpoint(endpointName).getClient();
	}
	
	@Override
	public IServer getServer(String endpointName){
		return getEndpoint(endpointName).getServer();
	}

	protected class GuiceEndpointModule extends GuiceModule{

		protected GuiceEndpointModule(LauncherContext context){
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
				bind(Key.get(IServer.class,new ClientImpl(config.getName()))).toInstance(endpoint.getServer());

				for (Class<?> c : sd.getClientInterfaces()){
					bind(Key.get((Class<Object>)c,new ClientImpl(config.getName()))).toProvider(new ClientProvider(config.getName(),c));
				}
			};
			
			bindListener(new AbstractMatcher<TypeLiteral<?>>() {
							public boolean matches(TypeLiteral<?> typeLiteral) {
								return typeLiteral.getRawType().isAnnotationPresent(Server.class);
						    }

						}, new TypeListener() {				
							    @Override
							    public <I> void hear(final TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
							        typeEncounter.register(new InjectionListener<I>() {
							            @Override
							            public void afterInjection(Object i) {
							                Export export=ClassUtils.getAnnotationOnClass(i.getClass(),Export.class);
							                if (export!=null){
								            	Server server=ClassUtils.getAnnotationOnClass(i.getClass(),Server.class);
							                	Endpoint endpoint=getEndpoint(server.value());
							                	Class<?>[] interf=ClassUtils.getInterfacesWithAnnotation(i.getClass(), Server.class);
												endpoint.getServer().export(getServerId(i),i,interf);
							                }
							            }
							        });
							    }
			      		 });
			

			bindInterceptor(Matchers.annotatedWith(Server.class), Matchers.annotatedWith(Export.class).or(Matchers.annotatedWith(Unexport.class)), new MethodInterceptor() {
				public Object invoke(MethodInvocation invocation) throws Throwable {
					Server server=ClassUtils.getAnnotationOnClass(invocation.getThis().getClass(), Server.class);
					if (server==null){
						throw new EndpointException("Annotation "+Server.class+" couldnt be read from class "+invocation.getThis().getClass().getSuperclass());
					}
					Endpoint endpoint=getEndpoint(server.value());
					Object result=invocation.proceed();
					if (invocation.getMethod().isAnnotationPresent(Export.class)){
						Class<?>[] interf=ClassUtils.getInterfacesWithAnnotation(invocation.getThis().getClass(), Server.class);
						endpoint.getServer().export(getServerId(invocation.getThis()), invocation.getThis(),interf);
					}else{
						endpoint.getServer().unexport(getServerId(invocation.getThis()),invocation.getThis());
					}
					return result;
				}
			});
		}
	}
	
	protected String getServerId(Object object){
		Id id=ClassUtils.getAnnotationOnInterface(object.getClass(), Id.class);
		if (id!=null){
			return id.value();
		}
		return null;
	}

	protected class ClientProvider implements Provider<Object>{
		private String name;
		private String id;
		private Class<?> interf;
		private Object client;
		
		ClientProvider(String name,Class<?> interf){
			this.name=name;
			this.interf=interf;
			Id aid=interf.getAnnotation(Id.class);
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
