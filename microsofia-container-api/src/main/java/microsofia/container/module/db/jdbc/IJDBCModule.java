package microsofia.container.module.db.jdbc;

import javax.sql.DataSource;

public interface IJDBCModule {

	public DataSource getDataSource(String name);
}
