package microsofia.container.application;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.inject.Inject;

import microsofia.container.ClassUtils;
import microsofia.container.module.db.jdbc.JDBC;
import microsofia.container.module.db.jpa.JPA;
import microsofia.container.module.endpoint.Client;
import microsofia.container.module.endpoint.EndpointDescriptor;
import microsofia.container.module.endpoint.Server;
import microsofia.container.module.jta.JTA;
import microsofia.container.module.property.Property;
import microsofia.container.module.property.PropertyDescriptor;

//TODO unit tests
/**
 * Helper configurator used to configure ApplicationDescritor by parsing classes that will be used
 * by the container. Instead of statically configure the ApplicationDescriptor, it is more dynamic to use
 * the configurator.
 * 
 * */
public class ApplicationDescriptorConfigurator {
	private ApplicationDescriptor applicationDescriptor;
	private Set<Class<?>> visited;
	
	/**
	 * Constructs a configurator for a given ApplicationDescriptor.
	 * 
	 * @param applicationDescritor the descriptor to configure
	 * */
	public ApplicationDescriptorConfigurator(ApplicationDescriptor applicationDescriptor){
		this.applicationDescriptor=applicationDescriptor;
		visited=new HashSet<>();
	}
	
	/**
	 * Returns the application descriptor.
	 * */
	public ApplicationDescriptor getApplicationDescriptor(){
		return applicationDescriptor;
	}
	
	/**
	 * Parse a class that will be used to configure the application descriptor.
	 * 
	 * @param c the class that will be parsed
	 * */
	public void parseClass(Class<?> c){
		//set used to avoid cyclic traversing
		Stack<Class<?>> toVisit=new Stack<>();
		
		toVisit.add(c);
		while (!toVisit.isEmpty()){
			Class<?> ctmp=toVisit.pop();
			visited.add(ctmp);
			parseClass(ctmp,toVisit);
		}
	}
	
	/*
	 * Parses one AnnotatedElement.
	 * */
	private void parseElement(Class<?> type,AnnotatedElement element,Stack<Class<?>> toVisit){	
		Inject inject=element.getAnnotation(Inject.class);
		com.google.inject.Inject gInject=element.getAnnotation(com.google.inject.Inject.class);
		if (inject!=null || gInject!=null){
			boolean required=(gInject!=null ? !gInject.optional() : true);
			
			//handling property
			Property p=element.getAnnotation(Property.class);
			if (p!=null){
				PropertyDescriptor pdes=(PropertyDescriptor)applicationDescriptor.properties().property(p.value()).required(required);
			
				if (Number.class.isAssignableFrom(type)){//TODO test that...
					pdes.setNumericType();

				}else if (!type.equals(String.class)){
					pdes.setObjectType(type);
				}
			}
			
			//handling jdbc
			JDBC jdbc=element.getAnnotation(JDBC.class);
			if (jdbc!=null){
				applicationDescriptor.jdbcs().jdbc(jdbc.value()).required(required);						
			}
			
			//handling jta
			JTA jta=element.getAnnotation(JTA.class);
			if (jta!=null){
				applicationDescriptor.jtas().jta(jta.value()).required(required);						
			}
				
			//handling jpa
			JPA jpa=element.getAnnotation(JPA.class);
			if (jpa!=null){
				applicationDescriptor.jpas().jpa(jpa.value()).required(required);//caller should fill the entities
			}
			
			//handling endpoints
			Client client=element.getAnnotation(Client.class);
			if (client!=null){
				EndpointDescriptor endDesc=(EndpointDescriptor)applicationDescriptor.endpoints().endpoint(client.value()).required(required);
				endDesc.addClientInterface(type);
			}
		}
	}
	
	protected void parseClass(Class<?> c,Stack<Class<?>> toVisit){
		Constructor<?>[] constructors=c.getDeclaredConstructors();
		if (constructors!=null){
			for (Constructor<?> constructor : constructors){
				Class<?>[] paramTypes=constructor.getParameterTypes();
				if (paramTypes!=null){
					for (int i=0;i<paramTypes.length;i++){
						Class<?> paramType=paramTypes[i];
						AnnotatedType at=constructor.getAnnotatedParameterTypes()[i];
						parseElement(paramType, at, toVisit);
					}
				}
			}
		}
		
		Field[] fields=c.getDeclaredFields();
		if (fields!=null){
			for (Field f : fields){
				if (!visited.contains(f.getType())){
					toVisit.add(f.getType());
				}
				parseElement(f.getType(), f, toVisit);
			}
		}
		
		if (c.isInterface()){
			Server server=c.getAnnotation(Server.class);
			if (server!=null && server.value().trim().length()>0){
				EndpointDescriptor endDesc=(EndpointDescriptor)applicationDescriptor.endpoints().endpoint(server.value());
				endDesc.addClientInterface(c);
			}

		}else{
			Server server=c.getAnnotation(Server.class);
			if (server!=null && server.value().trim().length()>0){
				EndpointDescriptor endDesc=(EndpointDescriptor)applicationDescriptor.endpoints().endpoint(server.value());
				Class<?>[] ci=ClassUtils.getInterfacesWithAnnotation(c, Server.class);
				if (ci!=null){
					for (Class<?> tc : ci){
						endDesc.addClientInterface(tc);
					}
				}
			}
		}
		
		Class<?> sc=c.getSuperclass();
		if (sc!=null && !visited.contains(sc)){
			toVisit.add(sc);
		}

		Class<?>[] inte=c.getInterfaces();
		if (inte!=null){
			for (Class<?> t : inte){
				if (t!=null && !visited.contains(t)){
					toVisit.add(t);
				}
			}
		}
	}
}
