package com.dev2next.cognitiveload;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Demonstrates complex exception handling chains with multiple sources of cognitive load and extraneous complexity.
 *
 * <p>This class contains methods that simulate reading a file, querying a database, and performing a calculation,
 * each of which can throw different exceptions. The exception handling in {@code processData()} introduces several
 * issues that increase cognitive load and reduce maintainability:
 *
 * <ul>
 *   <li><b>Swallowing and wrapping exceptions:</b> The {@code IOException} from {@code readFile()} is caught and wrapped
 *       in a generic {@code Exception}, losing the original cause and stack trace, which hinders debugging.</li>
 *   <li><b>Logging and continuing without propagation:</b> The {@code SQLException} from {@code queryDatabase()} is logged,
 *       but not rethrown or wrapped, making it unclear to callers whether an error occurred and potentially hiding failures.</li>
 *   <li><b>Hiding details in exception messages:</b> The {@code ArithmeticException} from {@code performCalculation()} is caught
 *       and replaced with a generic {@code Exception} with a vague message, forcing guesswork about the real issue.</li>
 *   <li><b>Top-level catch-all:</b> The {@code main} method catches all {@code Exception}s and logs only the message,
 *       further obscuring the root cause and stack trace of failures.</li>
 *   <li><b>Loss of exception context:</b> None of the rethrown exceptions preserve the original exception as the cause,
 *       making it difficult to trace the source of errors.</li>
 *   <li><b>Inconsistent error handling strategies:</b> Each block uses a different approach (swallow, log, wrap), increasing
 *       cognitive load for maintainers trying to understand the error flow.</li>
 * </ul>
 *
 * <p>These patterns collectively make the code harder to debug, maintain, and reason about, illustrating common pitfalls
 * in exception handling design.
 */
public class ComplexExceptionHandlingChains {

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

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ComplexExceptionHandlingChains.class.getName());

    public static void main(String[] args) {
        try {
            processData();
        } catch (Exception e) {
            // Top-level catch-all: hides real failure source
            logger.log(java.util.logging.Level.SEVERE, "Error: {0}", e.getMessage());
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
