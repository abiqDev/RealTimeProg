package Week11;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EvenNumberQueue {

    static ConcurrentLinkedQueue<Integer> queue =
            new ConcurrentLinkedQueue<>(); //need to import the thread-safe Queue class

    public static void main(String[] args)
            throws InterruptedException {

        Thread producer = new Thread(() -> {

            for(int i=1;i<=20;i++){

                if(i % 2 == 0){

                    queue.offer(i); //we need to adds the item to the queue

                    System.out.println(
                            "Produced: " + i);
                }
            }

        });

        Thread consumer = new Thread(() -> {

            while(true){

                Integer num = queue.poll();

                if(num != null){

                    System.out.println(
                            "Processed: " + num);
                }

                if(queue.isEmpty()){
                    break;
                }
            }

        });

        producer.start();

        producer.join();

        consumer.start();

        consumer.join();
    }
}