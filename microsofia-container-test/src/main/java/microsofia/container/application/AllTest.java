package microsofia.container.application;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import microsofia.container.module.db.jdbc.TestJDBCModule;
import microsofia.container.module.db.jpa.TestJPAModule;
import microsofia.container.module.endpoint.TestEndpointModule;
import microsofia.container.module.property.TestPropertyModule;

@RunWith(Suite.class)
@SuiteClasses({TestPropertyModule.class,TestJDBCModule.class,TestJPAModule.class,TestEndpointModule.class})
public class AllTest {

}
