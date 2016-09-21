package microsofia.container.module.endpoint;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import microsofia.container.module.AbstractTestModule;

public class TestRMIEndpointModule extends AbstractTestModule{
	@Inject
	private Sample sample;
	@Inject
	@Client("rmi1")
	private ISample isample;
	
	public TestRMIEndpointModule(){
	}
	
	@Test
	public void testClientSample2() throws RemoteException{
		Assert.assertNotNull("Couldn't inject service sample2.",isample);
		Assert.assertNotNull("Couldn't inject server sample2.",sample);
		Assert.assertEquals(isample.getHelloWorld(),"Hello");
	}
	
	@After
	public void cleanUp(){
		if (sample!=null){
			sample.stop();
		}
	}
	
	public static interface ISample extends Remote{
		public String getHelloWorld() throws RemoteException;
	}

	@Export
	@Server("rmi1")
	public static class Sample implements ISample{

		public Sample(){
		}

	    public String getHelloWorld() {
	        return "Hello";
	    }
	    
	    @Unexport
	    public void stop(){
	    }
	}
}
