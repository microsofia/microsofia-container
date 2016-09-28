package microsofia.container.module.endpoint;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

	public static <A extends Annotation> A getAnnotationOnInterface(Class<?> c,Class<A> ca){
		if (c.getInterfaces()!=null){
			for (Class<?> i : c.getInterfaces()){
				A a=i.getAnnotation(ca);
				if (a!=null){
					return a;
				}
			}
			for (Class<?> i : c.getInterfaces()){
				A a=getAnnotationOnClass(i, ca);
				if (a!=null){
					return a;
				}
			}
		}
		return null;
	}
	
	public static <A extends Annotation> A getAnnotationOnClass(Class<?> c,Class<A> ca){
		A a=c.getAnnotation(ca);
		while (a==null && c.getSuperclass()!=null){
			c=c.getSuperclass();
			a=c.getAnnotation(ca);
		}
		return a;
    }

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
}
