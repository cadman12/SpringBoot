package edu.pnu.test2;

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

class SomeClass implements SomeInterface1, SomeInterface2{

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
		Object proxy = Proxy.newProxyInstance(
				SomeInterface1.class.getClassLoader(),
				new Class[] { SomeInterface1.class, SomeInterface2.class },
				new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

						if (method.getDeclaringClass().equals(SomeInterface1.class)) {
							System.out.println("Before1 invocating method :" + method.getName());
							
							Object ret = method.invoke(someClass, args);
							
							System.out.println("After1 invocating method :" + method.getName());
							
							return ret;
						} else if (method.getDeclaringClass().equals(SomeInterface2.class)) {
							System.out.println("Before2 invocating method :" + method.getName());
							
							Object ret = method.invoke(someClass, args);
							
							System.out.println("After2 invocating method :" + method.getName());
							
							return ret;
						} else
							return null;
					}
				});
		
		SomeInterface1 si1 = (SomeInterface1)proxy;
		SomeInterface2 si2 = (SomeInterface2)proxy;
		
		someClass.doSomething11();
		si1.doSomething11();

		System.out.println("-".repeat(40));
		
		someClass.doSomething12();
		si1.doSomething12();
		
		System.out.println("-".repeat(40));
		
		someClass.doSomething21();
		si2.doSomething21();

		System.out.println("-".repeat(40));
		
		someClass.doSomething22();
		si2.doSomething22();
	}
}
