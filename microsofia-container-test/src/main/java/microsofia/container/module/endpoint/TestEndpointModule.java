package microsofia.container.module.endpoint;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.Assert;
import org.junit.Test;

import microsofia.container.module.AbstractTestModule;

public class TestEndpointModule extends AbstractTestModule{
	@Inject
	@Client("rest1")
	private ISample1 sample1;
	@Inject
	@Client("rest1")
	private ISample2 sample2;
	@Inject
	private Sample3 sample3;
	@Inject
	@Client("rest1")
	private ISample3 iSample3;
	
	
	public TestEndpointModule(){
	}
	
	@Test
	public void testSample1(){
		Assert.assertNotNull("Couldn't inject service sample1.",sample1);
	}

	@Test
	public void testSample2(){
		Assert.assertNotNull("Couldn't inject service sample2.",sample2);
	}
	
	@Test
	public void testExportAndCall(){
		sample3.start();
		Assert.assertEquals(iSample3.getHello(),"Hello");
	}
	
	@Path("helloworld1")
	public static interface ISample1{
		@POST
	    @Produces("text/plain")
		public void helloWorld();
	}

	@Path("helloworld2")
	public static interface ISample2{
		@POST
	    @Produces("text/plain")
		public void helloWorld();
	}
	
	@Path("helloworld3")
	public static interface ISample3{
	    @GET
	    @Produces("text/plain")
	    public String getHello();
	}

	@Server("rest1")
	@Path("helloworld3")
	public static class Sample3 implements ISample3{

		public Sample3(){
		}
		
		@Export
		public void start(){
		}
		
	    @GET
	    @Produces("text/plain")
	    public String getHello() {
	        return "Hello";
	    }
	}
}
