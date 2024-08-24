package edu.pnu.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Program {

	public static void main(String[] args) {

		SomeClass someClass = new SomeClass();
		SomeInterface proxy = (SomeInterface)Proxy.newProxyInstance(
				SomeInterface.class.getClassLoader(),
				new Class[] { SomeInterface.class },
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

						System.out.println("Before invocating method :" + method.getName());
						
						Object ret = method.invoke(someClass, args);
						
						System.out.println("After invocating method :" + method.getName());
						
						return ret;
					}
				});
		
		someClass.doSomething1();
		proxy.doSomething1();

		System.out.println("-".repeat(40));
		
		someClass.doSomething2();
		proxy.doSomething2();
	}
	
}
