package microsofia.container.module.atomix;

import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Injector;

import io.atomix.AtomixClient;
import io.atomix.AtomixReplica;
import io.atomix.variables.DistributedLong;
import microsofia.container.module.AbstractTestModule;

public class TestAtomixModule extends AbstractTestModule{
	@Inject
	private Injector injector; 
	private Replica1Provider replica1Provider;
	private Replica2Provider replica2Provider;
	private Replica3Provider replica3Provider;
	private ClientProvider clientProvider;
	
	public TestAtomixModule(){
	}
	
	@Test
	public void testAtomix() throws Exception{
		initReplica();
		
		DistributedLong dlong=replica1Provider.server1.getLong("test").get();
		dlong.set(new Long(1978)).get();
		
		Assert.assertEquals(new Long(1978), clientProvider.client.getLong("test").get().get().get());
	}
	
	protected void initReplica() throws Exception{
		Thread th1=new Thread(){
			public void run(){
				try{
					replica1Provider=injector.getInstance(Replica1Provider.class);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		th1.start();
	
		Thread th2=new Thread(){
			public void run(){
				try{
					replica2Provider=injector.getInstance(Replica2Provider.class);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		th2.start();
		
		Thread th3=new Thread(){
			public void run(){
				try{
					replica3Provider=injector.getInstance(Replica3Provider.class);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		th3.start();
		
		Thread th4=new Thread(){
			public void run(){
				try{
					clientProvider=injector.getInstance(ClientProvider.class);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		th4.start();
		
		th1.join();
		th2.join();
		th3.join();
		th4.join();
		
	}
	
	public static class ClientProvider{
		@Inject
		@Cluster("client")
		public AtomixClient client;

		public ClientProvider(){
		}
	}
	public static class Replica1Provider{
		@Inject
		@Cluster("server1")
		public AtomixReplica server1;

		public Replica1Provider(){
		}
	}
	public static class Replica2Provider{
		@Inject
		@Cluster("server2")
		public AtomixReplica server2;

		public Replica2Provider(){
		}
	}
	public static class Replica3Provider{
		@Inject
		@Cluster("server3")
		public AtomixReplica server3;

		public Replica3Provider(){
		}
	}
}
