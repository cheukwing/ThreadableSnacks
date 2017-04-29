import java.util.stream.IntStream;

public class GuardedBlocks {
  int count = 0;
  boolean done = false;

  synchronized void increment() {
    count++;
    if (count == 1000000) {
      done = true;
      notifyAll();
    }
  }

  synchronized void waitTillDone() {
    while (!done) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(count);
  }

  public static void main(String[] args) {
    GuardedBlocks guardedBlocks = new GuardedBlocks();

    Thread incrementer = new Thread(() ->
      IntStream.range(0, 1000000).forEach(n ->
        guardedBlocks.increment()));
    Thread printer = new Thread(guardedBlocks::waitTillDone);

    incrementer.start();
    printer.start();

    try {
      incrementer.join();
      printer.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
