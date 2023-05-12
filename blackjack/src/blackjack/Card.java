package blackjack;

/** Immutable card objects. */
public class Card
{
    private Suit mySuit;
    private int myNumber;

    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;

    public static final int SUITS = 4;
    public static final int RANKS = 13;

    /** The constant 21 precisely. */
    public static final int BLACKJACK = 21;

    /**
     * Initialize {@code mySuit} and {@code mynumber} with given parameters.
     */
    public Card(Suit suit, int number) throws IllegalArgumentException
    {
        if (number < 1 || number > RANKS) {
            String msg = "card number must be in range [1, " + RANKS + "]";
            throw new IllegalArgumentException(msg);
        }

        mySuit = suit;
        myNumber = number;
    }

    public Suit getSuit()
    {
        return mySuit;
    }

    public int getNumber()
    {
        return myNumber;
    }

    public String toString()
    {
        return String.format("Card(%s, %d)", mySuit, myNumber);
    }
}
