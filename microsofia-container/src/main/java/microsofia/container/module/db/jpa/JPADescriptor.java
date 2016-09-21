package microsofia.container.module.db.jpa;

import java.util.ArrayList;
import java.util.List;

import microsofia.container.module.ResourceDescriptor;

public class JPADescriptor extends ResourceDescriptor{
	private List<Class<?>> entitiesClass;

	public JPADescriptor(String name){
		super(name);
		entitiesClass=new ArrayList<Class<?>>();
	}

	public void addEntityClass(Class<?> c){
		entitiesClass.add(c);
	}
	
	public List<Class<?>> getEntitiesClass() {
		return entitiesClass;
	}

	public List<String> getEntitiesClassName() {
		List<String> names=new ArrayList<>();
		entitiesClass.forEach(it->{
			names.add(it.getName());
		});;
		return names;
	}
	
	public void setEntitiesClass(List<Class<?>> entitiesClass) {
		this.entitiesClass = entitiesClass;
	}
}
