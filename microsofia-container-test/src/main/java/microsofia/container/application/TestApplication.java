package microsofia.container.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.internal.TextListener;
import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import microsofia.container.Launcher;
import microsofia.container.LauncherContext;
import microsofia.container.application.AbstractApplication;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.db.jpa.JPADescriptor;
import microsofia.container.module.endpoint.EndpointDescriptor;
import microsofia.container.module.endpoint.TestMSofiaRMIEndpointModule;
import microsofia.container.module.endpoint.TestRestEndpointModule;
import microsofia.container.module.endpoint.TestRMIEndpointModule;
import microsofia.container.module.endpoint.TestRestEndpointModule.ISample2;
import microsofia.container.module.endpoint.TestRestEndpointModule.ISample3;
import microsofia.container.module.endpoint.TestRestEndpointModule.Sample2;
import microsofia.container.module.endpoint.TestRestEndpointModule.Sample3;
import microsofia.container.module.property.PropertyDescriptor;
import microsofia.container.module.property.TestPropertyModule;

public class TestApplication extends AbstractApplication{
	private static TestApplication instance;
	private static boolean fromUnitTest;
	private Injector injector;

	public TestApplication(){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.setType("testapp");

		JPADescriptor jpa1=new JPADescriptor("jpa1");
		applicationDescriptor.getJPAsDescriptor().addDescriptor(jpa1);
		
		JPADescriptor jpa2=new JPADescriptor("jpa2");
		applicationDescriptor.getJPAsDescriptor().addDescriptor(jpa2);
		
		EndpointDescriptor sd=new EndpointDescriptor("rest1");
		sd.addClientInterface(TestRestEndpointModule.ISample1.class);
		sd.addClientInterface(TestRestEndpointModule.ISample2.class);
		sd.addClientInterface(TestRestEndpointModule.ISample3.class);
		applicationDescriptor.getEndpointsDescriptor().addDescriptor(sd);
		
		EndpointDescriptor sd2=new EndpointDescriptor("rmi1");
		sd2.addClientInterface(TestRMIEndpointModule.ISample.class);		
		applicationDescriptor.getEndpointsDescriptor().addDescriptor(sd2);
		
		EndpointDescriptor sd3=new EndpointDescriptor("msofiarmi1");
		sd3.addClientInterface(TestMSofiaRMIEndpointModule.ISample.class);		
		sd3.addClientInterface(TestMSofiaRMIEndpointModule.ISample2.class);		
		applicationDescriptor.getEndpointsDescriptor().addDescriptor(sd3);
		
		EndpointDescriptor sd4=new EndpointDescriptor("msofiarmi2");
		sd4.addClientInterface(TestMSofiaRMIEndpointModule.ISample2.class);		
		applicationDescriptor.getEndpointsDescriptor().addDescriptor(sd4);
		
		PropertyDescriptor pd1=new PropertyDescriptor("k3");
		pd1.setRequired(true);
		pd1.setNumericType();
		applicationDescriptor.getPropertiesDescriptor().addDescriptor(pd1);
		
		
		PropertyDescriptor pd7=new PropertyDescriptor("k7");
		pd7.setObjectType(TestPropertyModule.Configuration.class);
		applicationDescriptor.getPropertiesDescriptor().addDescriptor(pd7);		
	}

	public Injector getInjector(){
		return injector;
	}
	
	@Override
	public void preInit(LauncherContext context) {
		instance=this;
		context.addGuiceModule(new AbstractModule() {
			
			@Override
			protected void configure() {
				bind(ISample3.class).to(Sample3.class);
				bind(ISample2.class).to(Sample2.class);
			}
		});
	}

	@Override
	public void postInit(LauncherContext context){
		this.injector=context.getInjector();
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
			Launcher launcher=new Launcher();
			launcher.setArguments(new String[]{"-property:key4=0000","-property:key5=00000","-property:key6=000000"});
			launcher.setApplicationConfig(readFrom(new FileInputStream(new File("settings.xml"))));
			launcher.start();
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
