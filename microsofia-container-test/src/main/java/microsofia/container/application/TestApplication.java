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
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;

import microsofia.container.Launcher;
import microsofia.container.LauncherContext;
import microsofia.container.application.AbstractApplication;
import microsofia.container.application.ApplicationDescriptor;
import microsofia.container.module.endpoint.IEndpointModule;
import microsofia.container.module.endpoint.EndpointDescriptor;
import microsofia.container.module.endpoint.TestEndpointModule;
import microsofia.container.module.endpoint.TestEndpointModule.ISample1;
import microsofia.container.module.endpoint.TestEndpointModule.ISample3;
import microsofia.container.module.endpoint.TestEndpointModule.Sample3;

public class TestApplication extends AbstractApplication{
	private static TestApplication instance;
	private Injector injector;

	public TestApplication(){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.setType("testapp");
		EndpointDescriptor sd=new EndpointDescriptor("rest1");
		sd.addClientInterface(TestEndpointModule.ISample1.class);
		sd.addClientInterface(TestEndpointModule.ISample2.class);
		sd.addClientInterface(TestEndpointModule.ISample3.class);

		applicationDescriptor.getEndpointsDescriptor().addDescriptor(sd);
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
			}
		});
	}

	@Override
	public void postInit(Injector injector){
		this.injector=injector;
	}

	public void run() throws Throwable{
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
	
	public static TestApplication getInstance() throws Throwable{
		if (instance==null){
			Launcher launcher=new Launcher();
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
