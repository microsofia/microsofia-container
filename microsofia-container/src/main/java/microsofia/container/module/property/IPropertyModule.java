package microsofia.container.module.property;

public interface IPropertyModule {

	public String getProperty(String name);

	public String replaceProperties(String str);
}
