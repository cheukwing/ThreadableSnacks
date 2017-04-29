package DiningPhilosophers;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/*
 * Just some practise with semaphores syntax.
 */

public class SemaphorePhilosophers {
  public static void main(String[] args) {
    Semaphore[] forks = new Semaphore[4];
    IntStream.range(0, 4).forEach(n -> forks[n] = new Semaphore(1));

    Semaphorsiphor[] philosophers = new Semaphorsiphor[4];
    Semaphorsiphor plato = new Semaphorsiphor("Plato", forks[0], forks[1]);
    philosophers[0] = plato;
    Semaphorsiphor socrates = new Semaphorsiphor("Socrates", forks[1], forks[2]);
    philosophers[1] = socrates;
    Semaphorsiphor aristotle = new Semaphorsiphor("Aristotle", forks[2], forks[3]);
    philosophers[2] = aristotle;
    Semaphorsiphor ptolemy = new Semaphorsiphor("Ptolemy", forks[0], forks[3]);
    philosophers[3] = ptolemy;

    Thread[] threads = new Thread[4];
    IntStream.range(0, 4).forEach(n -> {
      Thread thread = new Thread(philosophers[n]);
      threads[n] = thread;
      thread.start();
    });

    IntStream.range(0, 4).forEach(n -> {
      try {
        threads[n].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}

class Semaphorsiphor implements Runnable {
  private final String name;
  private final Semaphore left;
  private final Semaphore right;

  public Semaphorsiphor(String name, Semaphore left, Semaphore right) {
    this.name = name;
    this.left = left;
    this.right = right;
  }

  @Override
  public void run() {
    while (true) {
      try {
        left.acquire();
        try {
          right.acquire();
          System.out.println(name + " is eating.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          right.release();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        left.release();
      }

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
