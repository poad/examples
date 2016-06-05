package org.bitbucket.poad1010.sheso;

import java.lang.reflect.Method;

public class Handler {
	private final String methodName;
	private final Object object;
	private final Method method;
	Handler(String methodName, Object object, Method method) {
		this.methodName = methodName;
		this.object = object;
		this.method = method;
	}
	@Override
	public String toString() {
		return methodName();
	}
	public String methodName() {
		return methodName;
	}
	public Object object() {
		return object;
	}
	public Method method() {
		return method;
	}

}
