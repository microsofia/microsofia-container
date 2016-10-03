package microsofia.container.application;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import microsofia.container.module.db.jdbc.TestJDBCModule;
import microsofia.container.module.db.jpa.TestJPAModule;
import microsofia.container.module.endpoint.TestRestEndpointModule;
import microsofia.container.module.endpoint.TestMSofiaRMIEndpointModule;
import microsofia.container.module.endpoint.TestRMIEndpointModule;
import microsofia.container.module.property.TestPropertyModule;

/**
 * Suite containing all the tests.
 * */
@RunWith(Suite.class)
@SuiteClasses({TestRestEndpointModule.class,TestPropertyModule.class,TestJDBCModule.class,TestJPAModule.class,TestRMIEndpointModule.class,
				TestMSofiaRMIEndpointModule.class})
public class AllTest {

}
