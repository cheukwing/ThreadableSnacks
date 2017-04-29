import java.util.stream.IntStream;

public class SimpleSync {
  int count = 0;

  void increment() {
    count++;
  }

  public static void main(String[] args) {
    SimpleSync simpleSync = new SimpleSync();

    Thread thread1 = new Thread(() ->
      IntStream.range(0, 1000000).forEach(n ->
        simpleSync.increment()));
    Thread thread2 = new Thread(() ->
      IntStream.range(0, 1000000).forEach(n ->
        simpleSync.increment()));

    thread1.start();
    thread2.start();

    try {
      thread1.join();
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println(simpleSync.count);
  }
}

