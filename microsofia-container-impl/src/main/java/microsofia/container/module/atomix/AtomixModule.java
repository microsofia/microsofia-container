package microsofia.container.module.atomix;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.inject.Key;

import io.atomix.Atomix;
import io.atomix.AtomixClient;
import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.copycat.server.storage.Storage;
import microsofia.container.ContainerException;
import microsofia.container.InitializationContext;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.application.PropertyConfig;
import microsofia.container.module.ResourceBasedModule;

public class AtomixModule extends ResourceBasedModule<ClusterImpl, AtomixConfig,Atomix, AtomixDescriptor, AtomixsDescriptor> implements IAtomixModule{
	
	public AtomixModule(){
		super(Atomix.class,AtomixConfig.class);
	}

	@Override
	protected Atomix createResource(String name, AtomixConfig c) {
		AtomixDescriptor desc=null;
		if (moduleDescriptor!=null){
			desc=moduleDescriptor.getDescriptor(c.getName());
		}
		if (desc==null){
			throw new ContainerException("No atomix definition found for configuration "+c.getName());
		}
		try{
			List<Address> adr=new ArrayList<>();
			for (AtomixConfig.Member a : c.getMembers()){
				adr.add(new Address(a.getHost(), a.getPort()));
			}
			Properties properties=PropertyConfig.toPoperties(c.getProperties());
			
			Atomix atomix;
			if (c.getLocalMember()!=null){
				String localHost=(c.getLocalMember().getHost()!=null ? c.getLocalMember().getHost() : "localhost");
				String id=localHost+"/"+c.getLocalMember().getPort();
				
				AtomixReplica.Builder builder=AtomixReplica.builder(new Address(localHost,c.getLocalMember().getPort()),properties)
														   .withStorage(new Storage("logs/"+id));
				desc.getResourcesClass().forEach(it->{
					builder.withResourceTypes(it);
				});
				AtomixReplica replica=builder.build();
				if (desc.getAtomixConfigurator()!=null){
					desc.getAtomixConfigurator().configureAtomix(replica);
				}
				replica.bootstrap(adr).join();
				atomix=replica;
				
			}else{
				AtomixClient.Builder builder=AtomixClient.builder(properties);
				desc.getResourcesClass().forEach(it->{
					builder.withResourceTypes(it);
				});
				AtomixClient client=builder.build();
				if (desc.getAtomixConfigurator()!=null){
					desc.getAtomixConfigurator().configureAtomix(client);
				}
				client.connect(adr).get();
				atomix=client;
			}
			return atomix;

		}catch(Exception e){
			throw new ContainerException(e.getMessage(), e);
		}
	}
	
	@Override
	protected void stop(Atomix atomix){		
		if (atomix instanceof AtomixClient){
			((AtomixClient)atomix).close();
		}else{
			((AtomixReplica)atomix).shutdown();
		}
	}

	@Override
	protected ClusterImpl createResourceAnnotation(String name) {
		return new ClusterImpl(name);
	}

	@Override
	protected AtomixsDescriptor getResourceModuleDescriptor(ApplicationDescriptor desc) {
		return desc.getAtomixsDescriptor();
	}
	
	@Override
	protected List<AtomixConfig> getResourceConfig(InitializationContext context) {
		return context.getApplicationConfig().getAtomixConfigs();
	}

	@Override
	protected com.google.inject.AbstractModule createGuiceModule(InitializationContext context) {
		return new GuiceAtomixModule(context);
	}	
	
	@Override
	public AtomixClient getAtomixClient(String name){
		Atomix atomix=getResource(name);
		if (atomix instanceof AtomixClient){
			return (AtomixClient)atomix;
		}
		throw new ContainerException("Atomix resource "+name+" is a replica and not a client.");
	}

	@Override
	public AtomixReplica getAtomixReplica(String name){
		Atomix atomix=getResource(name);
		if (atomix instanceof AtomixReplica){
			return (AtomixReplica)atomix;
		}
		throw new ContainerException("Atomix resource "+name+" is a client and not a replica.");
	}
	
	protected class GuiceAtomixModule extends GuiceModule{

		protected GuiceAtomixModule(InitializationContext context){
			super(context);
		}

		@Override
		protected void configure(){			
			super.configure();
			//binding the public interface to itself
			bind(IAtomixModule.class).toInstance(AtomixModule.this);

			for (AtomixConfig ac : AtomixModule.this.configs.values()){
				if (ac.getLocalMember()!=null){
					bind(Key.get(AtomixReplica.class,createResourceAnnotation(ac.getName()))).toProvider(new GenericProvider(AtomixReplica.class, ac.getName()));
				}else{
					bind(Key.get(AtomixClient.class,createResourceAnnotation(ac.getName()))).toProvider(new GenericProvider(AtomixClient.class, ac.getName()));
				}
			}
		}
	}
}
