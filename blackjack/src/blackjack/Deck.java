package blackjack;
import java.util.Random;

public class Deck
{
    private Card[] myCards;
    private static final int MAX_CARDS = 52;
    /** Randomizer with the funny number as the seed (not anymore) */
    private Random rnd;
    private int numCards;

    public Deck()
    {
        myCards = new Card[MAX_CARDS];
        rnd = new Random();
        numCards = MAX_CARDS;
        int rankIndex = 0;
        for (Suit suit : Suit.values()) {
            for (int rank = 1; rank <= Card.RANKS; ++rank, ++rankIndex) {
                myCards[rankIndex] = new Card(suit, rank);
            }
        }
    }

    public void writeDeck()
    {
        String comma = ", ";
        for (Card card : myCards) {
            System.out.print(comma + card);
            comma = "";
        }
    }

    public void shuffle()
    {
        for (int i = 0; i < numCards; ++i) {
            int j;
            do {
                j = (int) (numCards * rnd.nextDouble());
            } while (i == j);
            swap(i, j);
        }
    }

    public void swap(int i, int j)
    {
        Card cardI = myCards[i];
        myCards[i] = myCards[j];
        myCards[j] = cardI;
    }

    /* Treating this as a FIFO... */
    public Card dealNextCard()
    {
        Card card = myCards[numCards - 1];
        myCards[--numCards] = null;
        return card;
    }
}
