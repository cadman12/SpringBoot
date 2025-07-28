package edu.pnu.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Trans {
}

interface MyInterface {
	void method();
}

class MyClass implements MyInterface {

	@Trans
	@Override
	public void method() {
		System.out.println("method()");
	}
}

public class Main {

	public static void main(String[] args) {
		
		MyClass mc = new MyClass();
//		mc.method();
		
		MyInterface mi = (MyInterface)Proxy.newProxyInstance(
				MyInterface.class.getClassLoader(),
				new Class[] { MyInterface.class },
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object ret;
						Method m = mc.getClass().getMethod(method.getName(), method.getParameterTypes());
						if (m.isAnnotationPresent(Trans.class)) {
							System.out.println("Before");
							ret = method.invoke(mc, args);
							System.out.println("After");
						}
						else
							ret = method.invoke(mc, args);
						return ret;
					}
				});
		mi.method();
	}
	
}
