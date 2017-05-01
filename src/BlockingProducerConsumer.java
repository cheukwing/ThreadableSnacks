import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class BlockingProducerConsumer {
  private BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

  void add(String item) {
    try {
      queue.put(item);
      System.out.println("Producer " + Thread.currentThread().getId() + " added " + item);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  void remove() {
    try {
      String item = queue.take();
      System.out.println("Consumer " + Thread.currentThread().getId() + " got " + item);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    BlockingProducerConsumer producerConsumer = new BlockingProducerConsumer();

    Thread[] producers = new Thread[10];
    IntStream.range(0, 10).forEach(n ->
          producers[n] = new Thread(() -> {
            int itemNo = 0;
            long id = Thread.currentThread().getId();
            while (true) {
              producerConsumer.add("P" + id + "-" + itemNo);
              itemNo++;
            }
          }));

    Thread[] consumers = new Thread[10];
    IntStream.range(0, 10).forEach(n ->
          consumers[n] = new Thread(() -> {
            while (true) {
              producerConsumer.remove();
            }
          }));

    IntStream.range(0, 10).forEach(n -> {
      producers[n].start();
      consumers[n].start();
    });

    IntStream.range(0, 10).forEach(n -> {
      try {
        producers[n].join();
        consumers[n].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
