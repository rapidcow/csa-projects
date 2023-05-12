import blackjack.*;

public class Driver
{
    public static void main(String[] args)
    {
        Deck d = new Deck();
        System.out.println("BEFORE SHUFFLE:");
        d.writeDeck();
        d.shuffle();
        System.out.println();
        System.out.println("AFTER SHUFFLE:");
        d.writeDeck();
    }
}
