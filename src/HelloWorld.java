import java.util.stream.IntStream;

public class HelloWorld {
  public static void main(String[] args) {
    Thread[] threads = new Thread[5];
    IntStream.range(0, 5).forEach(n ->
      threads[n] = new Thread(() ->
        System.out.println("Hello World " + Thread.currentThread().getId())));
    IntStream.range(0, 5).forEach(n ->
      threads[n].start());
  }
}
