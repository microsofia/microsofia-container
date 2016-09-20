package microsofia.container.module.property;

import java.math.BigDecimal;

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
	@Inject
	@Property("k3")
	public double v31;
	@Inject
	@Property("k3")
	public long v32;
	@Inject
	@Property("k3")
	public float v33;
	@Inject
	@Property("k3")
	public short v34;
	@Inject
	@Property("k3")
	public byte v35;
	@Inject
	@Property("k3")
	public BigDecimal v36;
	@com.google.inject.Inject(optional=true)
	@Property("k4")
	public String value;
	@Inject
	@Property("k5")
	private String v5;
	@Inject
	@Property("k6")
	private String v6;
	
	public TestPropertyModule(){
	}
	
	@Test
	public void testInjection(){		
		Assert.assertEquals("v1",v1);
		Assert.assertEquals("v2",v2);
		Assert.assertEquals(3,v3);
		Assert.assertTrue("Replacement of all variable of k4" , !value.contains("${"));
		Assert.assertTrue("Replacement of all variable of k5" , !v5.contains("${"));
		Assert.assertTrue("Replacement of all variable of k6" , !v6.contains("${"));
	}
}
