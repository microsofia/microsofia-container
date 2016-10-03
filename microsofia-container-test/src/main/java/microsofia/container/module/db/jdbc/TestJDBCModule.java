package microsofia.container.module.db.jdbc;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;

import microsofia.container.module.AbstractTestModule;

/**
 * Test the JDBC module.
 * */
public class TestJDBCModule extends AbstractTestModule{
	@Inject
	@JDBC("db1")
	private DataSource source1;
	@Inject
	@JDBC("db2")
	private DataSource source2;
	
	public TestJDBCModule(){
	}
	
	@Test
	public void testSource1(){
		Assert.assertNotNull("Couldn't inject database source db1.",source1);
		try {
			source1.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
			Assert.assertNull(e);
		}
	}

	@Test
	public void testSource2(){
		Assert.assertNotNull("Couldn't inject database source db2.",source2);
		try{
			source2.getConnection().close();
		}catch(SQLException e){
			e.printStackTrace();
			Assert.assertNull(e);
		}
	}
}
