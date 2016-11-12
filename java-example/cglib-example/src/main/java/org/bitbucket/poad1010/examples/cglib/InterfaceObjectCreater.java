package org.bitbucket.poad1010.examples.cglib;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class InterfaceObjectCreater {
	private final Enhancer e;

	public InterfaceObjectCreater() {
		e = new Enhancer();
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> interfaceClass, T handler) {
		e.setClassLoader(interfaceClass.getClassLoader());
		List<Class<?>> interfaces = new ArrayList<>();
		if (interfaceClass.isInterface()) {
			interfaces.add(interfaceClass);
		} else {
			e.setSuperclass(interfaceClass);
		}
		e.setInterfaces(interfaces.toArray(new Class[interfaces.size()]));

		e.setCallbacks(new Callback[] { new MethodInterceptor() {

			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//				System.out.println("class: " + obj.getClass().getName());
//				System.out.println("method: " + method.getName());
//				if (args != null && args.length > 0) {
//					Arrays.stream(args)
//							.map(arg -> arg.getClass().getName() + ": " + arg.toString())
//							.forEach(System.out::println);
//					;
//				} else {
//					System.out.println("args is empty");
//				}
				List<Class<?>> argTypes = objectsToClasses(args);
				if (hasMethod(handler.getClass(), method, argTypes)) {
					Method m = handler.getClass().getMethod(method.getName(), argTypes.toArray(new Class<?>[argTypes.size()]));
					m.invoke(handler, args);
				}

				return null;
			}
		} });

		return (T) e.create();
	}

	private static boolean hasMethod(Class<?> clazz, Method method, List<Class<?>> argTypes) {
		String methodName = method.getName();
		Method[] methods = clazz.getMethods();
		return Arrays.stream(methods).filter(m -> {
			if (m.getName().equals(methodName)) {
				List<Class<?>> types = Arrays.asList(m.getParameterTypes());
				return types.size() == argTypes.size() && types.containsAll(argTypes);
			}
			return false;
			}).count() > 0L;
	}

	private static List<Class<?>> objectsToClasses(Object[] objects) {
		if (objects != null && objects.length > 0) {
			return Arrays.stream(objects)
					.map(arg -> arg.getClass()).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
