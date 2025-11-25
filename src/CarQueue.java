import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * CarQueue class maintains a queue of random directions for the car
 * 0 = up, 1 = down, 2 = right, 3 = left
 */
public class CarQueue {
    private Queue<Integer> queue;
    private Random random;

    /**
     * Constructor for CarQueue
     * Initializes the queue with 5 or 6 random directions
     */
    public CarQueue() {
        queue = new LinkedList<>();
        random = new Random();
        
        // Place 5 or 6 numbers in the queue
        int initialCount = 5 + random.nextInt(2); // 5 or 6
        for (int i = 0; i < initialCount; i++) {
            queue.add(random.nextInt(4)); // 0, 1, 2, or 3
        }
    }

    /**
     * Adds 0, 1, 2 or 3 to queue
     * 0 = up
     * 1 = down
     * 2 = right
     * 3 = left
     * 
     * Uses a Runnable class that adds random directions and sleeps
     */
    public void addToQueue() {
        class QueueAdder implements Runnable {
            @Override
            public void run() {
                try {
                    // Add random directions to the queue
                    queue.add(random.nextInt(4)); // 0, 1, 2, or 3
                    // Sleep for a random amount of time
                    Thread.sleep(random.nextInt(1000) + 500); // 500-1500ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        // Create an instance of the runnable object
        QueueAdder adder = new QueueAdder();
        // Create a thread and start the thread
        Thread thread = new Thread(adder);
        thread.start();
    }

    /**
     * Delete and return an Integer from the queue
     * @return the next direction from the queue, or null if queue is empty
     */
    public Integer deleteQueue() {
        return queue.poll();
    }
}

