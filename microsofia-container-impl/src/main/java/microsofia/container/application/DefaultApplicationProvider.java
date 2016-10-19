package microsofia.container.application;

import java.util.ArrayList;
import java.util.List;

import microsofia.container.InitializationContext;

public class DefaultApplicationProvider extends ApplicationProvider {
	private List<IApplication> applications;

	public DefaultApplicationProvider(){
		applications=new ArrayList<>();
	}
	
	public void addApplication(IApplication app){
		applications.add(app);
	}

	public List<IApplication> getApplication(InitializationContext context){
		return applications;
	}
}
