package com.dev2next.cognitiveload;

import java.io.IOException;
import java.sql.SQLException;

public class ComplexExceptionHandlingChains {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ComplexExceptionHandlingChains.class.getName());

    public static void main(String[] args) {
        try {
            processData();
        } catch (Exception e) {
            // Top-level catch-all: hides real failure source
            logger.log(java.util.logging.Level.SEVERE, "Error: {0}", e.getMessage());
        }
    }

    static void processData() throws Exception {
        try {
            readFile();
        } catch (IOException e) {
            // Swallow and wrap: original cause lost
            throw new Exception("Failed to read file");
        }
        try {
            queryDatabase();
        } catch (SQLException e) {
            // Log and continue: diagnostics crippled
            logger.log(java.util.logging.Level.SEVERE, "Database error: {0}", e.getMessage());
        }
        try {
            performCalculation();
        } catch (ArithmeticException e) {
            // Hide details, force guesswork
            throw new Exception("Calculation failed");
        }
    }

    static void readFile() throws IOException {
        throw new IOException("File not found");
    }

    static void queryDatabase() throws SQLException {
        throw new SQLException("Connection refused");
    }

    static void performCalculation() {
        throw new ArithmeticException("Divide by zero");
    }
}
