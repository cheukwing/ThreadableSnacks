package DiningPhilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockedPhilosophers {
  public static void main(String[] args) {
    Lock[] forks = new Lock[4];
    IntStream.range(0, 4).forEach(n -> forks[n] = new ReentrantLock(true));

    LockyPhilo[] philosophers = new LockyPhilo[4];
    LockyPhilo plato = new LockyPhilo("Plato", forks[0], forks[1]);
    philosophers[0] = plato;
    LockyPhilo socrates = new LockyPhilo("Socrates", forks[1], forks[2]);
    philosophers[1] = socrates;
    LockyPhilo aristotle = new LockyPhilo("Aristotle", forks[2], forks[3]);
    philosophers[2] = aristotle;
    LockyPhilo ptolemy = new LockyPhilo("Ptolemy", forks[0], forks[3]);
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

class LockyPhilo implements Runnable {
  private final String name;
  private final Lock left;
  private final Lock right;

  public LockyPhilo(String name, Lock left, Lock right) {
    this.name = name;
    this.left = left;
    this.right = right;
  }

  @Override
  public void run() {
    while (true) {
      left.lock();
      try {
        right.lock();
        try {
          System.out.println(name + " is eating.");
        } finally {
          right.unlock();
        }
      } finally {
        left.unlock();
      }
    }
  }
}

