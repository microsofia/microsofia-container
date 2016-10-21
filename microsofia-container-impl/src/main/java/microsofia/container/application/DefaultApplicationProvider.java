package microsofia.container.application;

import java.util.ArrayList;
import java.util.List;

import microsofia.container.InitializationContext;

/**
 * Simple application provider implementation.
 * */
public class DefaultApplicationProvider extends ApplicationProvider {
	private List<IApplication> applications;

	/**
	 * Constructs an empty default application provider.
	 * */
	public DefaultApplicationProvider(){
		applications=new ArrayList<>();
	}
	
	/**
	 * Adds an application.
	 * */
	public void addApplication(IApplication app){
		applications.add(app);
	}

	/**
	 * Returns the list of the contained applications.
	 * */
	@Override
	public List<IApplication> getApplication(InitializationContext context){
		return applications;
	}
}
