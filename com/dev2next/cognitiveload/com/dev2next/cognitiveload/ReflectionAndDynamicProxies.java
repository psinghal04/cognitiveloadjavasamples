package com.dev2next.cognitiveload;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.Level;

public class ReflectionAndDynamicProxies {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(ReflectionAndDynamicProxies.class.getName());

    public static void main(String[] args) throws Exception {
        // Reflection and dynamic proxies introduce extraneous complexity and cognitive load by 
        // hiding type information and runtime behavior.
        // Developers must reason about method names, parameters, and invocation logic at runtime, 
        // increasing the risk of errors and making code harder to understand and maintain.
        Class<?> clazz = Class.forName("java.util.ArrayList");
        Object list = clazz.getDeclaredConstructor().newInstance();
        Method addMethod = clazz.getMethod("add", Object.class);
        addMethod.invoke(list, "Hello");
        addMethod.invoke(list, 42); // No compile-time type safety
        Method getMethod = clazz.getMethod("get", int.class);
        Object value = getMethod.invoke(list, 1); // Returns Integer
        LOGGER.log(Level.INFO, "Second element: {0}", value);

        // Dynamic Proxy: runtime method interception
        @SuppressWarnings("unchecked")
        List<String> proxyList = (List<String>) Proxy.newProxyInstance(
                ReflectionAndDynamicProxies.class.getClassLoader(),
                new Class<?>[]{List.class},
                new InvocationHandler() {
            private final List<String> inner = new ArrayList<>();

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LOGGER.log(Level.INFO, "Intercepted: {0}", method.getName());
                return method.invoke(inner, args);
            }
        });
        proxyList.add("World");
        proxyList.add("Proxy");
        LOGGER.log(Level.INFO, "Proxy contents: {0}", proxyList);
    }
}
