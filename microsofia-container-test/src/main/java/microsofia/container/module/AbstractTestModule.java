package microsofia.container.module;

import org.junit.Before;

import microsofia.container.application.TestApplication;

public class AbstractTestModule {

	public AbstractTestModule(){
	}
	
	@Before
	public void setup() throws Throwable{
		TestApplication.getInstance().getInjector().injectMembers(this);
	}

}
