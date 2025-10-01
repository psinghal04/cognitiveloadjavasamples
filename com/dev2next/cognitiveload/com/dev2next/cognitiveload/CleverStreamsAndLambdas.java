package com.dev2next.cognitiveload;

import java.util.*;
import java.util.logging.Logger;
import static java.util.stream.Collectors.*;

public class CleverStreamsAndLambdas {

    private static final Logger logger = Logger.getLogger(CleverStreamsAndLambdas.class.getName());

    static Map<String, String> badStreamUsage(List<User> users) {
        // Problems:
        // 1. Deeply nested streams and lambdas make it hard to follow the logic.
        // 2. Multiple collectors chained together reduce readability.
        // 3. Inline transformations (trim, toUpperCase) obscure intent.
        // 4. Limiting after grouping is confusing and may not be obvious to readers.
        // 5. The code mixes business logic and data transformation in one statement.
        return users.stream()
                .filter(u -> u.getOrders().stream().anyMatch(o -> o.getTotal() > 100))
                .collect(groupingBy(u -> u.getAddress().getCity(),
                        mapping(u -> u.getName().trim().toUpperCase(), toCollection(TreeSet::new))))
                .entrySet().stream().limit(5)
                .collect(toMap(Map.Entry::getKey, e -> String.join(",", e.getValue())));
    }

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

        logger.info("badStreamUsage:");
        Map<String, String> badResult = badStreamUsage(users);
        badResult.forEach((city, names) -> logger.log(java.util.logging.Level.INFO, "{0}: {1}", new Object[]{city, names}));

    }
}

class User {

    private final String name;
    private final Address address;
    private final List<Order> orders;

    public User(String name, Address address, List<Order> orders) {
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

class Address {

    private final String city;

    public Address(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}

class Order {

    private final double total;

    public Order(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}


