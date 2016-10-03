package microsofia.container.module;

import org.junit.Before;

import microsofia.container.application.TestApplication;

/**
 * Base parent for all test classes.
 * */
public class AbstractTestModule {

	public AbstractTestModule(){
	}
	
	@Before
	public void setup() throws Throwable{
		TestApplication.getInstance().getContainer().injectMembers(this);
	}

}
