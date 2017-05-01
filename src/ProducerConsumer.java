import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.IntStream;

public class ProducerConsumer {
  private Queue<String> queue = new ArrayDeque<>(10);

  synchronized void add(String item) {
    while (queue.size() == 10) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    queue.add(item);
    System.out.println("Producer " + Thread.currentThread().getId() + " added " + item);

    if (queue.size() == 1) {
      notifyAll();
    }
  }

  synchronized void remove() {
    while (queue.size() == 0) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    String item = queue.remove();
    System.out.println("Consumer " + Thread.currentThread().getId() + " got " + item);

    if (queue.size() == 9) {
      notifyAll();
    }
  }

  public static void main(String[] args) {
    ProducerConsumer producerConsumer = new ProducerConsumer();

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
