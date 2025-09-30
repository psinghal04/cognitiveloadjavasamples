/**
 * Demonstrates improved concurrency handling using {@link AtomicInteger} and {@code volatile} variables
 * to reduce cognitive overload compared to manual synchronization and memory model quirks.
 * <p>
 * <b>Improvements over {@code ConcurrencyConstructsMemoryModelQuirks}:</b>
 * <ul>
 *   <li>Uses {@link AtomicInteger} for thread-safe counter updates, eliminating the need for 
 *       explicit locks or synchronization.</li>
 *   <li>Employs a {@code volatile} boolean flag to signal thread termination, ensuring visibility across 
 *       threads without complex memory barriers.</li>
 *   <li>Minimizes cognitive load by leveraging high-level concurrency constructs, making the code 
 *       easier to reason about and maintain.</li>
 *   <li>Provides clear separation of writer and reader logic, with atomic operations 
 *       guaranteeing correctness and up-to-date values.</li>
 *   <li>Reduces risk of subtle concurrency bugs related to stale data or race conditions.</li>
 * </ul>
 * 
 * This approach allows developers to focus on application logic rather than low-level thread coordination,
 * resulting in safer and more maintainable concurrent code.
 */
package com.dev2next.cognitiveload;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class BetterConcurrency {

     private static final Logger LOGGER = Logger.getLogger(BetterConcurrency.class.getName());

    // Use AtomicInteger for thread-safe updates
    private static final AtomicInteger sharedCounter = new AtomicInteger(0);
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                sharedCounter.incrementAndGet(); // Atomic, no data race
            }
            running = false;
        });

        Thread reader = new Thread(() -> {
            int lastSeen = -1;
            while (running) {
                int current = sharedCounter.get(); // Always up-to-date
                if (current != lastSeen) {
                    LOGGER.log(java.util.logging.Level.INFO, "Reader sees: {0}", current);
                    lastSeen = current;
                }
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
        
        LOGGER.log(java.util.logging.Level.INFO, "Final value: {0}", sharedCounter.get());
    }
}
