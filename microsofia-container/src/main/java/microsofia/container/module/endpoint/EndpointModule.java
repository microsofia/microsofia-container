package microsofia.container.module.endpoint;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.common.util.concurrent.Service;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;

import microsofia.container.LauncherContext;
import microsofia.container.module.ResourceBasedModule;

public class EndpointModule extends ResourceBasedModule<ClientImpl, EndpointConfig,Endpoint> implements IEndpointModule{

	public EndpointModule() {
		super(Endpoint.class);
	}
	
	@Override
	protected List<EndpointConfig> getResourceConfig(LauncherContext context) {
		return context.getCurrentApplicationConfig().getEndpointConfigs();
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
	
	public Endpoint getEndpoint(String name){
		return getResource(name);
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

				for (Class<?> c : sd.getClientInterfaces()){
					bind(Key.get((Class<Object>)c,new ClientImpl(config.getName()))).toProvider(new ClientProvider(config.getName(),c));
				}
			};
			
			//TODO intercept unexport
			bindInterceptor(Matchers.annotatedWith(Server.class), Matchers.annotatedWith(Export.class), new MethodInterceptor() {
				public Object invoke(MethodInvocation invocation) throws Throwable {
					Server server=invocation.getThis().getClass().getSuperclass().getAnnotation(Server.class);//TODO hack, cause we know it is directly the parent...
					Endpoint endpoint=getEndpoint(server.value());//TODO allow default endpoint
					Object result=invocation.proceed();
					endpoint.getServer().export(invocation.getThis());
					return result;
				}
			});
		}
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
