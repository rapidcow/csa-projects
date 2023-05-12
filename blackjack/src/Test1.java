import blackjack.*;
import java.util.Arrays;

public class Test1
{
    public static void main(String[] args)
    {
        Player p = new Player("sus");
        p.addCard(new Card(Suit.SPADES, 3));
        p.addCard(new Card(Suit.HEARTS, 1));
        System.out.println(Arrays.toString(p.getHand()));
        System.out.println(p.getNumHand() + " " + p.isSoft());

        p.emptyHand();
        System.out.println(Arrays.toString(p.getHand()));
        System.out.println(p.getNumHand() + " " + p.isSoft());
        p.addCard(new Card(Suit.DIAMONDS, 5));
        p.addCard(new Card(Suit.CLUBS, 13));
        System.out.println(Arrays.toString(p.getHand()));
        System.out.println(p.getNumHand() + " " + p.isSoft());
    }
}
