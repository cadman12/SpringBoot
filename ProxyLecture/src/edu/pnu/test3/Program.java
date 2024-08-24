package edu.pnu.test3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface SomeInterface1{
	void doSomething11();
	void doSomething12();
}

interface SomeInterface2 {
	void doSomething21();
	void doSomething22();
}

interface SomeInterface extends SomeInterface1, SomeInterface2 {}

class SomeClass implements SomeInterface{

	@Override
	public void doSomething11() {
		System.out.println("doSomething11");
	}

	@Override
	public void doSomething12() {
		System.out.println("doSomething12");
	}

	@Override
	public void doSomething21() {
		System.out.println("doSomething21");		
	}

	@Override
	public void doSomething22() {
		System.out.println("doSomething22");		
	}
}

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
		
		someClass.doSomething11();
		proxy.doSomething11();

		System.out.println("-".repeat(40));
		
		someClass.doSomething12();
		proxy.doSomething12();
		
		System.out.println("-".repeat(40));
		
		someClass.doSomething21();
		proxy.doSomething21();

		System.out.println("-".repeat(40));
		
		someClass.doSomething22();
		proxy.doSomething22();
	}
}
