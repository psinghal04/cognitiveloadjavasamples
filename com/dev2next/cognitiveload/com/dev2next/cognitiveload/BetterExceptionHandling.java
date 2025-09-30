package com.dev2next.cognitiveload;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class BetterExceptionHandling {

    private static final Logger LOGGER = Logger.getLogger(BetterExceptionHandling.class.getName());

    public static void main(String[] args) {
        try {
            processData();
            // Improved exception handling:
            //   *  Clear separation of concerns by handling each exception type specifically.
            //   *  Preserving original exception types and messages.
            //   *  Avoiding unnecessary wrapping and swallowing of exceptions.
            //   *  Making diagnostics clear and failure paths explicit.
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
