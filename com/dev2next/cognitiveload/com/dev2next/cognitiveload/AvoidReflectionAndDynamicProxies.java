package com.dev2next.cognitiveload;

import java.util.*;

public class AvoidReflectionAndDynamicProxies {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(AvoidReflectionAndDynamicProxies.class.getName());

    public static void main(String[] args) {
        // Uses direct instantiation and method calls for type safety, avoiding reflection.
        // Replaces dynamic proxies with explicit interface implementation (LoggingList), making behavior clear and maintainable.
        // Logging is done in overridden methods, so interception is explicit and easy to trace.
        // This approach avoids hidden failure paths and cognitive overhead caused by reflection and proxies.
        // Use direct instantiation and method calls for type safety
        List<Object> list = new ArrayList<>();
        list.add("Hello");
        list.add(42); // Compiler enforces type safety if you use List<String>
        Object value = list.get(1); // No need for reflection
        LOGGER.log(java.util.logging.Level.INFO, "Second element: {0}", value);

        // Use explicit interface implementation instead of dynamic proxies
        List<String> safeList = new LoggingList();
        safeList.add("World");
        safeList.add("Proxy");
        LOGGER.log(java.util.logging.Level.INFO, "SafeList contents: {0}", safeList);
    }

    // Explicit implementation with clear logging
    static class LoggingList extends ArrayList<String> {

        @Override
        public boolean add(String s) {
            LOGGER.log(java.util.logging.Level.INFO, "Intercepted: add"); // Clear, explicit logging
            return super.add(s);
        }
        // You can override other methods as needed
    }
}
