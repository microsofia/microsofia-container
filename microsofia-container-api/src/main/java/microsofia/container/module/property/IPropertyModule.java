package microsofia.container.module.property;

public interface IPropertyModule {

	public String getStringProperty(String name);

	public Object getObjectProperty(String name);
	
	public String replaceProperties(String str);
}
