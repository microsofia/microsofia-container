package microsofia.container.module.db.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import microsofia.container.module.ResourceDescriptor;

/**
 * JPA resource descriptor. The entity classes are needed in order to create the EntityManagerFactory.
 * */
public class JPADescriptor extends ResourceDescriptor{
	private Set<Class<?>> entitiesClass;

	public JPADescriptor(String name){
		super(name);
		entitiesClass=new HashSet<Class<?>>();
	}
	
	/**
	 * Adds an entity class, builder style.
	 * */
	public JPADescriptor entity(Class<?> c){
		entitiesClass.add(c);
		return this;
	}

	/**
	 * Adds an entity class.
	 * */
	public void addEntityClass(Class<?> c){
		entitiesClass.add(c);
	}
	
	/**
	 * Returns the entity classes.
	 * */
	public Set<Class<?>> getEntitiesClass() {
		return entitiesClass;
	}

	/**
	 * Returns the entity class names.
	 * */
	public List<String> getEntitiesClassName() {
		List<String> names=new ArrayList<>();
		entitiesClass.forEach(it->{
			names.add(it.getName());
		});;
		return names;
	}

	/**
	 * Sets the entity classes.
	 * */
	public void setEntitiesClass(Set<Class<?>> entitiesClass) {
		this.entitiesClass = entitiesClass;
	}
}
