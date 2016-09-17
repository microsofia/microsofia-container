package microsofia.container.module.db.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import microsofia.container.application.TestApplication;

@RunWith(TestApplication.JUnitLauncher.class)
public class TestJPAModule {
	@Inject
	@JPA("jpa1")
	private EntityManagerFactory emf1;
	@Inject
	@JPA("jpa1")
	private SessionFactory sf1;
	@Inject
	@JPA("jpa2")
	private EntityManagerFactory emf2;
	@Inject
	@JPA("jpa2")
	private SessionFactory sf2;
	
	public TestJPAModule(){
	}

	@Test
	public void testJPA1(){
		Assert.assertNotNull("Couldn't inject JPA source db1.",emf1);
		Assert.assertNotNull("Couldn't inject JPA source db1.",sf1);
		/*TODO do some entities read/write
		 * try {
			source1.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}*/
	}

	@Test
	public void testJPA2(){
		Assert.assertNotNull("Couldn't inject JPA source db2.",emf2);
		Assert.assertNotNull("Couldn't inject JPA source db2.",sf2);
		/*TODO 
		try{
			source2.getConnection().close();
		}catch(SQLException e){
			e.printStackTrace();
			Assert.assertNull(e);
		}*/
	}

	public static TestJPAModule instance;
}
