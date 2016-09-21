package microsofia.container.module.endpoint;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import microsofia.container.module.AbstractTestModule;

public class TestRestEndpointModule extends AbstractTestModule{
	@Inject
	@Client("rest1")
	private ISample1 sample1;
	@Inject
	@Client("rest1")
	private ISample2 isample2;
	@Inject
	@Client("rest1")
	private ISample3 iSample3;
	@Inject
	private Sample2 sample2;
	@Inject
	private Sample3 sample3;
	
	public TestRestEndpointModule(){
	}
	
	@Test
	public void testClientSample1(){
		Assert.assertNotNull("Couldn't inject service sample1.",sample1);
	}

	@Test
	public void testClientSample2(){
		Assert.assertNotNull("Couldn't inject service sample2.",isample2);
		Assert.assertNotNull("Couldn't inject server sample2.",sample2);
		Assert.assertEquals(isample2.getHelloWorld(),"Hello");
	}
	
	@Test
	public void testExportAndCall(){
		sample3.start();
		Assert.assertEquals(iSample3.getHello(),"Hello");
	}
	
	@After
	public void cleanUp(){
		if (sample2!=null){
			sample2.stop();
		}
		if (sample3!=null){
			sample3.stop();
		}
	}
	
	@Path("helloworld1")
	public static interface ISample1{
		@POST
	    @Produces("text/plain")
		@Path("sample1")
		public void helloWorld();
	}

	@Path("helloworld2")
	public static interface ISample2{
		@GET
	    @Produces("text/plain")
		@Path("sample1")
		public String getHelloWorld();
	}
	
	@Path("helloworld3")
	public static interface ISample3{
	    @GET
	    @Produces("text/plain")
	    @Path("sample3")
	    public String getHello();
	}
	
	@Export
	@Server("rest1")
	@Path("helloworld2")
	public static class Sample2 implements ISample2{

		public Sample2(){
		}
				
	    @GET
	    @Produces("text/plain")
	    @Path("sample1")
	    public String getHelloWorld() {
	        return "Hello";
	    }
	    
	    @Unexport
	    public void stop(){
	    }
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
	    @Path("sample3")
	    public String getHello() {
	        return "Hello";
	    }
	    
	    @Unexport
	    public void stop(){
	    }
	}
}
