package microsofia.container.module.endpoint;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import microsofia.container.module.AbstractTestModule;

public class TestMSofiaRMIEndpointModule extends AbstractTestModule{
	@Inject
	private Sample sample;
	@Inject
	private Sample2 sample2;
	@Inject
	@Client("msofiarmi1")
	private ISample2 isample2;
	
	public TestMSofiaRMIEndpointModule(){
	}
	
	@Test
	public void testClientSample2() throws Exception{
		Assert.assertNotNull("Couldn't inject service sample2.",isample2);
		Assert.assertNotNull("Couldn't inject server sample2.",sample);
		Assert.assertEquals(isample2.returnSample(sample),sample);
		try{
			sample2.throwsException();
			Assert.assertTrue(false);
		}catch(Exception e){
		}
	}
	
	@After
	public void cleanUp(){
		if (sample!=null){
			sample.stop();
		}
		if (sample2!=null){
			sample2.stop();
		}
	}
	
	@Server
	public static interface ISample{
		
		public String getHelloWorld();		
	}
	
	@Server
	public static interface ISample2{
		
		public ISample returnSample(ISample sample);

		public void throwsException() throws Exception;
	}

	@Export
	@Server("msofiarmi1")
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

	@Export
	@Server("msofiarmi2")
	public static class Sample2 implements ISample2{

		public Sample2(){
		}

		public ISample returnSample(ISample sample){
			return sample;
		}

		public void throwsException() throws Exception{
	    	throw new Exception("Sample!");
	    }
		
	    @Unexport
	    public void stop(){
	    }
	}
}
