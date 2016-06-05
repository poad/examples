package org.bitbucket.poad1010.sheso.delegate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bitbucket.poad1010.sheso.Handler;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class InterfaceDelegate {
	private final Enhancer e;

	public InterfaceDelegate() {
		e = new Enhancer();
	}

	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> interfaceClass, Map<String, Handler> handlers) {
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
				Handler handler = handlers.get(method.getName());
				Method callbackMethod = handler.method();
				Object callbackObject = handler.object();
				return callbackMethod.invoke(callbackObject, args);
			}
		} });

		return (T) e.create();
	}
}
