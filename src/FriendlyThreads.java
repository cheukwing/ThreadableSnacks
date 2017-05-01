public class FriendlyThreads {
  public static void main(String[] args) {
    Thread foo = new Thread(() -> {
      synchronized (Thread.currentThread()) {
        try {
          Thread.currentThread().wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println("I HAVE AWAKENED FROM MY SLUMBER MORTALS.");
      }
    });

    Thread bar = new Thread(() -> {
      System.out.println("IT IS TIME TO WAKEN THE BEAST...");
      synchronized (foo) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println("SHE RISES...");
        foo.notify();
      }
    });

    foo.start();
    bar.start();

    try {
      bar.join();
      foo.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}


