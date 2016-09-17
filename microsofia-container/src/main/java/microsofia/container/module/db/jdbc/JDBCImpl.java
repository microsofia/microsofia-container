package microsofia.container.module.db.jdbc;

import java.lang.annotation.Annotation;

public class JDBCImpl implements JDBC{
	private String name;
	
	public JDBCImpl(String name){
		this.name=name;
	}
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return JDBC.class;
	}

	@Override
	public String value() {
		return name;
	}	

	 @Override 
	 public boolean equals(Object o) {
		 if (!(o instanceof JDBC)) {
			 return false;
	     }

	     JDBC other = (JDBC) o;
	     return name.equals(other.value());
	 }

	 @Override 
	 public int hashCode() {
		 return (127 * "value".hashCode()) ^ name.hashCode();
	 }

	 @Override 
	 public String toString() {
		 return "@" + JDBC.class.getName() + "(value=" + name + ")";
	 }
}
