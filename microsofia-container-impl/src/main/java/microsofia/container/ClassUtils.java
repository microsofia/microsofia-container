package microsofia.container;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Class introspection utilities.
 * */
public class ClassUtils {

	/**
	 * Searches for an annotation in the interfaces of a class and all the interfaces parents.
	 * 
	 * @param c the class to search on
	 * @param ca the annotation type
	 * @return the annotation instance
	 * */
	public static <A extends Annotation> A getAnnotationOnInterface(Class<?> c,Class<A> ca){
		Class<?>[] intef;
		if (c.isInterface()){
			intef=new Class<?>[]{c};
		}else{
			intef=c.getInterfaces();
		}
		if (intef!=null){
			for (Class<?> i : intef){
				A a=i.getAnnotation(ca);
				if (a!=null){
					return a;
				}
			}
			for (Class<?> i : intef){
				A a=getAnnotationOnClass(i, ca);
				if (a!=null){
					return a;
				}
			}
		}
		return null;
	}
	
	/**
	 * Searches for an annotation in a class and all its superclass.
	 * 
	 * @param c the class to search in
	 * @param ca the annotation type
	 * @return the annotation instance
	 * */
	public static <A extends Annotation> A getAnnotationOnClass(Class<?> c,Class<A> ca){
		A a=c.getAnnotation(ca);
		while (a==null && c.getSuperclass()!=null){
			c=c.getSuperclass();
			a=c.getAnnotation(ca);
		}
		return a;
    }

	/**
	 * Searches for all interfaces in a class having an annotation.
	 * 
	 * @param c the class to search in
	 * @param ca the annotation type
	 * @return the list of interfaces
	 * */
	public static <A extends Annotation> Class<?>[] getInterfacesWithAnnotation(Class<?> c,Class<A> ca){
		List<Class<?>> interf=new ArrayList<Class<?>>();
		while (c!=null){
			if (c.getInterfaces()!=null){
				for (Class<?> i : c.getInterfaces()){
					if (getAnnotationOnClass(i, ca)!=null){
						interf.add(i);
					}
				}

			}
			c=c.getSuperclass();
		}
		return interf.toArray(new Class<?>[0]);
    }
	
	public static <A extends Annotation> Method getMethod(Object o,Class<A> ca){
		for (Method m : o.getClass().getMethods()){
			if (m.isAnnotationPresent(ca)){
				return m;
			}
		}
		return null;
	}
}
