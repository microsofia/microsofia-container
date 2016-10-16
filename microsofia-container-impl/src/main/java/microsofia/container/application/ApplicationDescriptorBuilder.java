package microsofia.container.application;

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

public class ApplicationDescriptorBuilder {
	private ApplicationDescriptor applicationDescriptor;
	private Set<Class<?>> visited;
	
	public ApplicationDescriptorBuilder(ApplicationDescriptor applicationDescriptor){
		this.applicationDescriptor=applicationDescriptor;
		visited=new HashSet<>();
	}
	
	public ApplicationDescriptor getApplicationDescriptor(){
		return applicationDescriptor;
	}
	
	public void visit(Class<?> c){
		Stack<Class<?>> toVisit=new Stack<>();
		
		toVisit.add(c);
		while (!toVisit.isEmpty()){
			Class<?> ctmp=toVisit.pop();
			visited.add(ctmp);
			visit(ctmp,toVisit);
		}
	}
	
	protected void visit(Class<?> c,Stack<Class<?>> toVisit){
		Field[] fields=c.getDeclaredFields();//TODO visit also constructors...
		if (fields!=null){
			for (Field f : fields){
				if (!visited.contains(f.getType())){
					toVisit.add(f.getType());
				}

				Inject inject=f.getAnnotation(Inject.class);
				com.google.inject.Inject gInject=f.getAnnotation(com.google.inject.Inject.class);
				if (inject!=null || gInject!=null){
					boolean required=(gInject!=null ? !gInject.optional() : true);
					
					//handling property
					Property p=f.getAnnotation(Property.class);
					if (p!=null){
						PropertyDescriptor pdes=(PropertyDescriptor)applicationDescriptor.properties().property(p.value()).required(required);
					
						if (Number.class.isAssignableFrom(f.getType())){
							pdes.setNumericType();

						}else if (!f.getType().equals(String.class)){
							pdes.setObjectType(f.getType());
						}
					}
					
					//handling jdbc
					JDBC jdbc=f.getAnnotation(JDBC.class);
					if (jdbc!=null){
						applicationDescriptor.jdbcs().jdbc(jdbc.value()).required(required);						
					}
					
					//handling jta
					JTA jta=f.getAnnotation(JTA.class);
					if (jta!=null){
						applicationDescriptor.jtas().jta(jta.value()).required(required);						
					}
						
					//handling jpa
					JPA jpa=f.getAnnotation(JPA.class);
					if (jpa!=null){
						applicationDescriptor.jpas().jpa(jpa.value()).required(required);//caller should fill the entities
					}
					
					//handling endpoints
					Client client=f.getAnnotation(Client.class);
					if (client!=null){
						EndpointDescriptor endDesc=(EndpointDescriptor)applicationDescriptor.endpoints().endpoint(client.value()).required(required);
						endDesc.addClientInterface(f.getType());
					}
				}
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
