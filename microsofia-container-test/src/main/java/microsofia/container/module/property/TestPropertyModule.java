package microsofia.container.module.property;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import microsofia.container.application.TestApplication;
import microsofia.container.module.AbstractTestModule;

public class TestPropertyModule extends AbstractTestModule{
	@Inject
	@Property("k1")
	private String v1;
	@Inject
	@Property("k2")
	private String v2;
	@Inject
	@Property("k3")
	public int v3;
	@com.google.inject.Inject(optional=true)
	@Property("k4")
	public String value;
	
	public TestPropertyModule(){
	}
	
	@Test
	public void testInjection(){		
		Assert.assertEquals("v1",v1);
		Assert.assertEquals("v2",v2);
		Assert.assertEquals(3,v3);
	}
}
