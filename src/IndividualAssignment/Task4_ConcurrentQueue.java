package IndividualAssignment;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Task4_ConcurrentQueue {
    public static void main(String[] args) throws InterruptedException {

        // Create ConcurrentLinkedQueue
        ConcurrentLinkedQueue<String> requestQueue = new ConcurrentLinkedQueue<>();

        // Add registration requests into the queue
        requestQueue.offer("307895 - Abiq - Register STIWK3014");
        requestQueue.offer("307001 - Ali Hassan - Register STIWK2114");
        requestQueue.offer("307002 - Siti Aisyah - Drop STIWK3024");
        requestQueue.offer("307003 - Ahmad Farid - Register STIWK2124");
        requestQueue.offer("307004 - Nurul Hidayah - Update STIWK3034");
        requestQueue.offer("307005 - Mohamad Zikri - Register STIWK2134");
        requestQueue.offer("307006 - Fatin Izzati - Drop STIWK3044");
        requestQueue.offer("307007 - Hafiz Ramli - Register STIWK2144");
        requestQueue.offer("307008 - Syafiqah Noor - Register STIWK3054");
        requestQueue.offer("307009 - Izzatul Iman - Update STIWK2154");

        System.out.println("Total requests in queue: " + requestQueue.size());

        // Display the first request waiting to be processed (peek does NOT remove)
        System.out.println("\n===== FIRST REQUEST (PEEK) =====");
        System.out.println("First request: " + requestQueue.peek());
        System.out.println("Queue size after peek: " + requestQueue.size()); // unchanged

        // Process and remove requests one by one until the queue is empty
        System.out.println("\n===== PROCESSING REQUESTS (FIFO) =====");
        while (!requestQueue.isEmpty()) {
            String request = requestQueue.poll(); // retrieves AND removes
            System.out.println("Processing: " + request);
            Thread.sleep(100); // simulate processing time
        }

        // Confirm queue is empty
        System.out.println("\nAll requests processed.");
        System.out.println("Queue is empty: " + requestQueue.isEmpty());
    }
}
