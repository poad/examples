package org.bitbucket.poad1010.sheso;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bitbucket.poad1010.sheso.annotation.Delegate;
import org.bitbucket.poad1010.sheso.delegate.InterfaceDelegate;

public class InterfaceObject {
	private InterfaceDelegate delegator;
	public InterfaceObject() {
		delegator = new InterfaceDelegate();
	}

	public <T> T newInstance(Class<T> interfaceClass) {
		Map<String, Handler> handlers = createHandlers(interfaceClass);
		return delegator.create(interfaceClass, handlers);
	}

	private static <T> Map<String, Handler> createHandlers(Class<T> interfaceClass) {
		Method[] methods = interfaceClass.getMethods();
		return Arrays.stream(methods)
		.map(method -> {
			try {
				Delegate delegate = method.getAnnotation(Delegate.class);
				Class<?> bindClass = delegate.delegate();
				String bindMethodName = delegate.method();
				Method bindMethod;
				if (!bindMethodName.isEmpty()) {
					if (!hasMethod(interfaceClass, bindMethodName, Arrays.asList(method.getParameterTypes()))) {
						// TODO ERROR
						throw new RuntimeException();
					}
						bindMethod = bindClass.getMethod(bindMethodName, method.getParameterTypes());
				} else {
					bindMethod = bindClass.getMethod(method.getName(), method.getParameterTypes());
				}
					return new Handler(method.getName(), bindClass.newInstance(), bindMethod);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		})
		.collect(Collectors.toMap(info -> info.methodName(), info -> info));
	}
	
	private static boolean hasMethod(Class<?> clazz, String methodName, List<Class<?>> argTypes) {
		Method[] methods = clazz.getMethods();
		return Arrays.stream(methods).filter(m -> {
			if (m.getName().equals(methodName)) {
				List<Class<?>> types = Arrays.asList(m.getParameterTypes());
				return types.size() == argTypes.size() && types.containsAll(argTypes);
			}
			return false;
			}).count() > 0L;
	}
}
