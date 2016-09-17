package microsofia.container.application;

import java.io.File;
import java.io.FileInputStream;

import org.junit.runner.Computer;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.google.inject.Injector;

import microsofia.container.Launcher;
import microsofia.container.LauncherContext;
import microsofia.container.application.AbstractApplication;
import microsofia.container.application.ApplicationDescriptor;

public class TestApplication extends AbstractApplication{
	private static TestApplication instance;
	private Injector injector;

	public TestApplication(){
		applicationDescriptor=new ApplicationDescriptor();
		applicationDescriptor.setType("testapp");
	}

	@Override
	public void preInit(LauncherContext context) {
		instance=this;
	}

	@Override
	public void postInit(Injector injector){
		this.injector=injector;
	}

	public void run() throws Throwable{
		Computer computer=new Computer();
		JUnitCore core=new JUnitCore();
		Result result=core.run(computer,AllTest.class);

		if (result.getFailureCount()>0){
			System.out.println("It failed!!!!!!!!!!!!!!!!!!");
			result.getFailures().forEach(it->{it.getException().printStackTrace();});
		}else{
			System.out.println("SUCCESS");
		}
		
	}
	
	private static TestApplication getInstance() throws Throwable{
		if (instance==null){
			Launcher launcher=new Launcher();
			//TODO avoid having 2 config files
			launcher.setApplicationConfig(ApplicationConfig.readFrom(new FileInputStream(new File("application.xml"))));
			launcher.start();
		}
		return instance;
	}

	public static class JUnitLauncher extends BlockJUnit4ClassRunner {


	    public JUnitLauncher(final Class<?> klass) throws org.junit.runners.model.InitializationError {
	        super(klass);
	    }

	    @Override
	    public final Object createTest() throws Exception {
	        final Object obj = super.createTest();
	        try {
				getInstance().injector.injectMembers(obj);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Exception(e.getMessage(),e);
			}
	        return obj;
	    }
	}

	
}
