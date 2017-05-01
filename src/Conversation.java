public class Conversation {
  public static void main(String[] args) {
    TurnToTalk turnToTalk = new TurnToTalk();
    Thread steve = new Thread(new Steve(turnToTalk));
    Thread billy = new Thread(new Billy(turnToTalk));

    steve.start();
    billy.start();

    try {
      steve.join();
      billy.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class TurnToTalk {}

class Steve implements Runnable {
  private TurnToTalk turnToTalk;

  public Steve(TurnToTalk turnToTalk) {
    this.turnToTalk = turnToTalk;
  }

  @Override
  public void run() {
    synchronized (turnToTalk) {
      System.out.println("Hello Billy.");
      turnToTalk.notifyAll();
      waitNext();
      System.out.println("updog.");
      turnToTalk.notifyAll();
      waitNext();
      System.out.println("Not much how about you?");
      turnToTalk.notifyAll();
    }
  }

  private void waitNext() {
    try {
      turnToTalk.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class Billy implements Runnable {
  private TurnToTalk turnToTalk;

  public Billy(TurnToTalk turnToTalk) {
    this.turnToTalk = turnToTalk;
  }

  @Override
  public void run() {
    synchronized (turnToTalk) {
      System.out.println("Hello Steve.");
      turnToTalk.notifyAll();
      waitNext();
      System.out.println("What's updog?");
      turnToTalk.notifyAll();
      waitNext();
      System.out.println("Go away.");
    }
  }

  private void waitNext() {
    try {
      turnToTalk.wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

