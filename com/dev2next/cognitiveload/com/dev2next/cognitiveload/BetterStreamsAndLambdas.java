package com.dev2next.cognitiveload;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Logger;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toCollection;

public class BetterStreamsAndLambdas {

    // This version simplifies stream and lambda usage by:
    // - Using clear variable names and straightforward method references
    // - Avoiding nested or overly clever stream operations that obscure intent
    // - Presenting logic in a linear, easy-to-follow manner
    // Compared to CleverStreamsAndLambdas, this reduces extraneous complexity and cognitive load,
    // making it easier for others to understand, maintain, and debug the code.
    static Map<String, String> improvedStreamUsage(List<User> users) {
        Map<String, TreeSet<String>> highValueOrdersByCity = users.stream()
                .filter(BetterStreamsAndLambdas::hasHighValueOrder)
                .collect(groupingBy(
                        BetterStreamsAndLambdas::cityOf,
                        HashMap::new,
                        mapping(u -> normalizeName(u.getName()), toCollection(TreeSet::new))
                ));

        return highValueOrdersByCity.entrySet().stream()
                .filter(e -> e.getKey() != null)
                .limit(5)
                .collect(LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), String.join(",", e.getValue())),
                        Map::putAll);
    }

    private static boolean hasHighValueOrder(User u) {
        return u.getOrders().stream().anyMatch(o -> o.getTotal() > 100);
    }

    private static String normalizeName(String name) {
        return name == null ? "" : name.trim().toUpperCase();
    }

    private static String cityOf(User u) {
        return u.getAddress().getCity();
    }

    static class User {

        private final String name;
        private final Address address;
        private final List<Order> orders;

        User(String name, Address address, List<Order> orders) {
            this.name = name;
            this.address = address;
            this.orders = orders;
        }

        public String getName() {
            return name;
        }

        public Address getAddress() {
            return address;
        }

        public List<Order> getOrders() {
            return orders;
        }
    }

    static class Address {

        private final String city;

        public Address(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }

    static class Order {

        private final double total;

        public Order(double total) {
            this.total = total;
        }

        public double getTotal() {
            return total;
        }
    }

    private static final Logger logger = Logger.getLogger(BetterStreamsAndLambdas.class.getName());

    public static void main(String[] args) {
        // Sample data
        List<User> users = Arrays.asList(
                new User(" Alice ", new Address("New York"), Arrays.asList(new Order(120), new Order(80))),
                new User("Bob", new Address("Los Angeles"), Arrays.asList(new Order(50), new Order(200))),
                new User("Charlie", new Address("New York"), Arrays.asList(new Order(30))),
                new User("Diana", new Address("Chicago"), Arrays.asList(new Order(150))),
                new User("Eve", new Address("Los Angeles"), Arrays.asList(new Order(110))),
                new User("Frank", new Address("Miami"), Arrays.asList(new Order(99))),
                new User("Grace", new Address("Chicago"), Arrays.asList(new Order(101)))
        );

        logger.info("\nimprovedStreamUsage:");
        Map<String, String> goodResult = improvedStreamUsage(users);
        goodResult.forEach((city, names) -> logger.log(java.util.logging.Level.INFO, "{0}: {1}", new Object[]{city, names}));
    }
}
