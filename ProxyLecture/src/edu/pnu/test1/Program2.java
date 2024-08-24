package edu.pnu.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Program2 {

	public static void main(String[] args) {

		SomeInterface proxy = (SomeInterface) Proxy.newProxyInstance(SomeInterface.class.getClassLoader(),
				new Class[] { SomeInterface.class }, new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

						System.out.println("Before invocating method :" + method.getName());

						Object ret = method.invoke(new SomeClass(), args);

						System.out.println("After invocating method :" + method.getName());

						return ret;
					}
				});

		proxy.doSomething1();

		System.out.println("-".repeat(40));

		proxy.doSomething2();
	}

}
