package microsofia.container.module.db.jdbc;

import javax.sql.DataSource;

/**
 * Public interface of the JDBC module. It can be injected in case there is
 * a use case where injecting the {@link DataSource} is not enough. <br />
 * <br />
 * Example: <br />
 * <pre>
<br />
public class Sample{<br />
 ...
&#64;Inject
IJDBCModule jdbcModule;   

... 

}
 * </pre>
 */
public interface IJDBCModule {

	/**
	 * Returns the datasource for a given database name.
	 * 
	 * @param name the name of the database
	 * @return the datasource that connects to the database
	 * */
	public DataSource getDataSource(String name);
}
