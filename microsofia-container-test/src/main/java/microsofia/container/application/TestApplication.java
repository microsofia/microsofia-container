package microsofia.container.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.internal.TextListener;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.google.inject.AbstractModule;

import microsofia.container.Container;
import microsofia.container.ContainerBuilder;
import microsofia.container.InitializationContext;
import microsofia.container.application.AbstractApplication;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.endpoint.TestMSofiaRMIEndpointModule;
import microsofia.container.module.endpoint.TestRestEndpointModule;
import microsofia.container.module.endpoint.TestRMIEndpointModule;
import microsofia.container.module.endpoint.TestRestEndpointModule.ISample2;
import microsofia.container.module.endpoint.TestRestEndpointModule.ISample3;
import microsofia.container.module.endpoint.TestRestEndpointModule.Sample2;
import microsofia.container.module.endpoint.TestRestEndpointModule.Sample3;
import microsofia.container.module.property.TestPropertyModule;

public class TestApplication extends AbstractApplication{
	@Inject
	private Container container;
	private static TestApplication instance;
	private static boolean fromUnitTest;

	public TestApplication(){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.type("testapp");

		applicationDescriptor.jpas().jpa("jpa1");//TODO test with .entity(c)
		applicationDescriptor.jpas().jpa("jpa2");//TODO test with .entity(c)

		applicationDescriptor.endpoints().endpoint("rest1")
										 .client(TestRestEndpointModule.ISample1.class)
										 .client(TestRestEndpointModule.ISample2.class)
										 .client(TestRestEndpointModule.ISample3.class);
		
		applicationDescriptor.endpoints().endpoint("rmi1")
									     .client(TestRMIEndpointModule.ISample.class);		

		applicationDescriptor.endpoints().endpoint("msofiarmi1")
										 .client(TestMSofiaRMIEndpointModule.ISample.class)
										 .client(TestMSofiaRMIEndpointModule.ISample2.class);

		applicationDescriptor.endpoints().endpoint("msofiarmi2")
										 .client(TestMSofiaRMIEndpointModule.ISample2.class);		

		applicationDescriptor.properties().property("k3").numericType().required(true);

		applicationDescriptor.properties().property("k7").objectType(TestPropertyModule.Configuration.class);
		
		applicationDescriptor.atomixs().atomix("client");
		applicationDescriptor.atomixs().atomix("server1");
		applicationDescriptor.atomixs().atomix("server2");
		applicationDescriptor.atomixs().atomix("server3");
	}
	
	public Container getContainer(){
		return container;
	}
	
	@Override
	public void preInit(InitializationContext context) {
		instance=this;
		context.addGuiceModule(new AbstractModule() {
			
			@Override
			protected void configure() {
				bind(ISample3.class).to(Sample3.class);
				bind(ISample2.class).to(Sample2.class);
			}
		});
	}

	public void run() throws Throwable{
		if (!fromUnitTest){
			Computer computer=new Computer();
			JUnitCore core=new JUnitCore();
			core.addListener(new TextListener(System.out));
			Result result=core.run(computer,AllTest.class);
	
			if (result.getFailureCount()>0){
				System.out.println("It failed!!!!!!!!!!!!!!!!!!");
				result.getFailures().forEach(it->{it.getException().printStackTrace();});
			}else{
				System.out.println("SUCCESS");
			}
		}	
	}
	
	public static TestApplication getInstance() throws Throwable{
		if (instance==null){
			fromUnitTest=true;
			ContainerBuilder builder=new ContainerBuilder();
			builder.arguments(new String[]{"-property:key4=0000","-property:key5=00000","-property:key6=000000"});
			builder.applicationConfig(readFrom(new FileInputStream(new File("settings.xml"))));
			builder.build().start();
		}
		return instance;
	}	

	@XmlRootElement(name="settings")
	public static class Settings{
		@XmlElement
		public ApplicationConfig application;
	}

	private static JAXBContext jaxbContext=null;
	static{
		try{
			jaxbContext=JAXBContext.newInstance(Settings.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static ApplicationConfig readFrom(InputStream in) throws Exception{
		Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
		return ((Settings)unmarshaller.unmarshal(in)).application;
	}
}
