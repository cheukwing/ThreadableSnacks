package ChildrenOfTheAtom;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIncrement {
  AtomicInteger x;

  public AtomicIncrement() {
    x = new AtomicInteger();
    x.set(0);
  }

  public static void main(String[] args) {
    AtomicIncrement atomicIncrement = new AtomicIncrement();

    Thread[] threads = new Thread[2];
    IntStream.range(0, 2).forEach(n ->
      threads[n] = new Thread(() ->
        IntStream.range(0, 1000000).forEach(i ->
          atomicIncrement.x.incrementAndGet())));

    IntStream.range(0, 2).forEach(n -> threads[n].start());

    IntStream.range(0, 2).forEach(n -> {
      try {
        threads[n].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    System.out.println(atomicIncrement.x.get());
  }
}
