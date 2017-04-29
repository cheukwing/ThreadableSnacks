package DiningPhilosophers;

import java.util.stream.IntStream;

public class SynchronizedPhilosophers {
  public static void main(String[] args) {
    Fork[] forks = new Fork[4];
    IntStream.range(0, 4).forEach(n -> forks[n] = new Fork());

    Philosopher[] philosophers = new Philosopher[4];
    Philosopher plato = new Philosopher("Plato", forks[0], forks[1]);
    philosophers[0] = plato;
    Philosopher socrates = new Philosopher("Socrates", forks[1], forks[2]);
    philosophers[1] = socrates;
    Philosopher aristotle = new Philosopher("Aristotle", forks[2], forks[3]);
    philosophers[2] = aristotle;
    Philosopher ptolemy = new Philosopher("Ptolemy", forks[0], forks[3]);
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

class Philosopher implements Runnable {
  private final String name;
  private final Fork left;
  private final Fork right;

  public Philosopher(String name, Fork left, Fork right) {
    this.name = name;
    this.left = left;
    this.right = right;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (left) {
        synchronized (right) {
          System.out.println(name + " is eating.");
        }
      }

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class Fork {}
