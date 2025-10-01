package com.dev2next.cognitiveload;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class BetterExceptionHandling {

    private static final Logger LOGGER = Logger.getLogger(BetterExceptionHandling.class.getName());

    public static void main(String[] args) {
        try {
            processData();
            //   * Reduces extraneous complexity by handling each exception type in its own block, 
            //     making the code easier to follow.
            //   * Lowers cognitive load by making error sources and responses explicit, 
            //     so developers can quickly understand failure paths.
            //   * Preserves original exception types and messages for accurate diagnostics.
            //   * Avoids unnecessary wrapping or swallowing of exceptions, keeping error handling 
            //     straightforward.
        } catch (IOException e) {
            LOGGER.severe(String.format("File error: %s", e.getMessage()));
        } catch (SQLException e) {
            LOGGER.severe(String.format("Database error: %s", e.getMessage()));
        } catch (ArithmeticException e) {
            LOGGER.severe(String.format("Calculation error: %s", e.getMessage()));
        }
    }

    static void processData() throws IOException, SQLException, ArithmeticException {
        readFile(); // throws IOException
        queryDatabase(); // throws SQLException
        performCalculation(); // throws ArithmeticException
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
