/**
 * The {@code GodClass} exemplifies the "God Class" anti-pattern in object-oriented design,
 * where a single class takes on numerous unrelated responsibilities such as data storage,
 * business logic, UI logic, logging, file I/O, and configuration management.
 *
 * <p>
 * God classes introduce extraneous complexity by tightly coupling disparate concerns,
 * making the codebase harder to understand, maintain, and extend. Developers must keep track
 * of multiple contexts and behaviors within one class, which increases cognitive load and
 * the likelihood of errors.
 * </p>
 *
 * <p>
 * This structure violates the Single Responsibility Principle and leads to poor modularity,
 * making refactoring and testing more difficult. The example here is intentionally designed
 * to highlight how God classes can hinder clarity and scalability in software projects.
 * </p>
 */
package com.dev2next.cognitiveload;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class GodClass {
    // Data storage
    private final List<String> users = new ArrayList<>();
    private final Map<String, Integer> accounts = new HashMap<>();
    private final List<String> logs = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(GodClass.class.getName());

    // Business logic
    public void addUser(String name) {
        users.add(name);
        log("Added user: " + name);
    }

    public void deposit(String name, int amount) {
        accounts.put(name, accounts.getOrDefault(name, 0) + amount);
        log("Deposited " + amount + " to " + name);
    }

    public void withdraw(String name, int amount) {
        int balance = accounts.getOrDefault(name, 0);
        if (balance >= amount) {
            accounts.put(name, balance - amount);
            log("Withdrew " + amount + " from " + name);
        } else {
            log("Insufficient funds for " + name);
        }
    }

    // UI logic
    public void printUsers() {
        LOGGER.log(java.util.logging.Level.INFO, "Users: {0}", users);
    }

    public void printAccounts() {
        LOGGER.log(java.util.logging.Level.INFO, "Accounts: {0}", accounts);
    }

    // Logging
    private void log(String message) {
        logs.add(message);
        LOGGER.log(java.util.logging.Level.INFO, "LOG: {0}", message);
    }

    // File I/O
    public void saveLogsToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String log : logs) {
                writer.write(log);
                writer.newLine();
            }
        }
    }

    // Configuration
    private final Properties config = new Properties();

    public void loadConfig(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            config.load(fis);
        }
    }

    public String getConfig(String key) {
        return config.getProperty(key);
    }
}