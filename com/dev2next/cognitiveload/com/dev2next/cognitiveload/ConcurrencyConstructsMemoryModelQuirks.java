/**
 * Demonstrates quirks of Java's memory model and concurrency constructs.
 * <p>
 * This class showcases how data races and visibility issues can occur when
 * shared variables are accessed by multiple threads without proper synchronization.
 * The {@code sharedCounter} variable is incremented by one thread and read by another,
 * illustrating possible stale reads and race conditions. The {@code running} flag is
 * marked {@code volatile} to ensure visibility between threads.
 * <p>
 * 
 * Such subtle concurrency bugs increase extraneous complexity and cognitive load for developers,
 * as reasoning about thread safety, visibility, and ordering requires deep understanding of the
 * Java Memory Model. Without clear synchronization, code becomes harder to maintain, debug, and verify.
 */
package com.dev2next.cognitiveload;

import java.util.logging.Logger;

public class ConcurrencyConstructsMemoryModelQuirks {

    private static final Logger LOGGER = Logger.getLogger(ConcurrencyConstructsMemoryModelQuirks.class.getName());

    private static volatile boolean running = true;
    private static int sharedCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                sharedCounter++; // Data race: not synchronized
            }
            running = false;
        });

        Thread reader = new Thread(() -> {
            int lastSeen = -1;
            while (running) {
                if (sharedCounter != lastSeen) {
                    // Stale reads: may not see latest value
                    LOGGER.log(java.util.logging.Level.INFO, "Reader sees: {0}", sharedCounter);
                    lastSeen = sharedCounter;
                }
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
        
        LOGGER.log(java.util.logging.Level.INFO, "Final value: {0}", sharedCounter);
    }
}
