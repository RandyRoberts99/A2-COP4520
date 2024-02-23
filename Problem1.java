import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class MinotaurBirthdayParty {

  public final int numGuests;
  public final AtomicInteger cupcakesEaten;
  private final Semaphore entrySemaphore;
  private final boolean[] guestsEntered;
  private volatile boolean partyOver;

  public MinotaurBirthdayParty(int numGuests) {
    this.numGuests = numGuests;
    this.entrySemaphore = new Semaphore(1);
    this.cupcakesEaten = new AtomicInteger(0);
    this.guestsEntered = new boolean[numGuests];
    this.partyOver = false;
  }

  public void enterLabyrinth(String guestName) throws InterruptedException {
    entrySemaphore.acquire(); // Ensure only one guest enters at a time

    if (partyOver) {
      entrySemaphore.release();
      return;
    }

    int guestIndex = Integer.parseInt(guestName.split(" ")[1]) - 1;

    System.out.println(guestName + " enters the labyrinth.");

    // Simulate finding cupcake and deciding whether to eat it or leave it
    if (!guestsEntered[guestIndex]) {
      System.out.println(guestName + " finds a cupcake and decides to eat it.");
      guestsEntered[guestIndex] = true;
      cupcakesEaten.incrementAndGet(); // Atomically increment the cupcakes eaten count
    } else {
      System.out.println(guestName + " leaves the cupcake for another guest.");
    }

    if (cupcakesEaten.get() == numGuests) {
      System.out.println(
        "All guests have entered the labyrinth at least once. Party successful!"
      );
      partyOver = true;
    }

    entrySemaphore.release(); // Release semaphore to allow next guest to enter
  }
}

class Guest extends Thread {

  private final String name;
  private final MinotaurBirthdayParty party;

  public Guest(String name, MinotaurBirthdayParty party) {
    this.name = name;
    this.party = party;
  }

  @Override
  public void run() {
    try {
      party.enterLabyrinth(name);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class Problem1 {

  public static void main(String[] args) {
    int numGuests = 5; // You can choose a specific number of guests
    MinotaurBirthdayParty party = new MinotaurBirthdayParty(numGuests);

    while (party.cupcakesEaten.get() < numGuests) {
      int i = ThreadLocalRandom.current().nextInt(1, numGuests + 1);
      Guest guest = new Guest("Guest " + i, party);
      guest.start();
    }
  }
}
