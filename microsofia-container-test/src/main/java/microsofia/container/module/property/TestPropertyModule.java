package microsofia.container.module.property;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Assert;
import org.junit.Test;
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
	@Inject
	@Property("k7")
	private Configuration conf;
	
	public TestPropertyModule(){
	}
	
	@Test
	public void testInjection(){
		Assert.assertEquals("v1",v1);
		Assert.assertEquals("v2",v2);
	}
	
	@Test
	public void testIntegerInjection(){
		Assert.assertEquals(3,v3);
	}
	
	@Test
	public void testReplacementProperty(){
		Assert.assertTrue("Replacement of all variable of k4" , !value.contains("${"));
		Assert.assertTrue("Replacement of all variable of k5" , !v5.contains("${"));
		Assert.assertTrue("Replacement of all variable of k6" , !v6.contains("${"));
	}
	
	@Test
	public void testObjectProperty(){
		Assert.assertNotNull(conf);
		Assert.assertEquals("f1", conf.f1);
		Assert.assertEquals("f2", conf.f2);
	}

	@XmlRootElement(name="config")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Configuration{
		@XmlElement
		public String f1;
		@XmlElement
		public String f2;
	}
}
