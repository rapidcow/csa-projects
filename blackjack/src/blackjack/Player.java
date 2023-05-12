package blackjack;

public class Player
{
    /* For the case of 1+1+1+1+2+2+2+2+3+3+3, you would have 11 cards
     * and precisely 21 cards... but that should never happen? */
    private static final int MAX_CARDS = 11;
    private final Card[] hand;
    private int numCard;
    private String name;

    /* cardSum is the sum of all the cards EXCEPT aces.
     * aceCount is the number of aces.
     * numHand is the calculated value taking into account of
     * soft/hard aces (a soft ace is one that acts as 11, and it's
     * "soft" because it can also act as 1 when draw too many cards
     * so that when it acts as 11, the value exceeds 21); the
     * isSoft tells you if either one of the aces is soft.
     *
     * These values are updated only when either emptyHand() or addCard()
     * is called.
     */
    private int aceCount;
    private int cardSum;
    private boolean isSoft;
    private int numHand;

    public Player(String name)
    {
        this.name = name;
        hand = new Card[MAX_CARDS];
        /*
         * Chained assignment can be unexpected... in this case
         * it does not reset the values?
         *
         * See: https://stackoverflow.com/a/9440923
         *      https://stackoverflow.com/a/9440897
         */
        numCard = 0;
        aceCount = 0;
        cardSum = 0;
        /* Initialize computed values */
        numHand = 0;
        isSoft = false;
    }

    /**
     * Empty array {@code hand} (set all elements of hand to {@code null}).
     * Set {@code numCard} to 0.
     */
    public void emptyHand()
    {
        /* I always liked this joke someone pulled off with their code
         * as seen here: https://stackoverflow.com/a/20754605
         * In that case I can even write
         *
         *     while (numCard --> 0)  hand[numCard] = null;
         *
         * As if we have a new operator --> to symbolically denote that
         * numCard goes to 0!  (Not really though, numCard actually goes
         * to -1 instead of 0, because after the final iteration when it
         * checks for 0 > 0, it exits the loop BUT the post-decrement
         * operator still brings numCard down by 1.)
         *
         * The following code is not any better though... in fact I'm
         * surprised it also fit on just one line!
         *
         * (Also: https://stackoverflow.com/a/27672749,
         *        https://stackoverflow.com/a/2604267,
         *        https://stackoverflow.com/a/21683994)
         */
        while (numCard > 0) hand[--numCard] = null;
        aceCount = 0;
        cardSum = 0;
        /* Reset computed values */
        numHand = 0;
        isSoft = false;
    }

    /**
     * Add parameter aCard to hand.
     * Increment numCards by 1.
     * Call {@link getNumHand()} method and return {@code true} if the total
     * is less than or equal to 21; otherwise return {@code false}.
     */
    public boolean addCard(Card aCard)
    {
        hand[numCard++] = aCard;
        int value = aCard.getNumber();
        if (value == Card.JACK || value == Card.QUEEN ||
            value == Card.KING)
        {
            cardSum += 10;
        }
        else if (value == Card.ACE) {
            aceCount += 1;
        }
        else {
            cardSum += value;
        }

        computeNumHand();
        return numHand >= Card.BLACKJACK;
    }

    private void computeNumHand()
    {
        /* Determine soft/hard ace */
        /* Hypothetically assume every ace is 1.  The number of
         * 10's we can fit in the DIFFERENCE with BLACKJACK (21)
         * is the number of aces we can interpret as 11.
         *
         * We do not want to deal with negative differences though,
         * so let numHand go over BLACKJACK (21) if it has to.
         */
        numHand = cardSum + aceCount;
        int diff = Math.max(0, Card.BLACKJACK - numHand);
        int elevenAces = Math.min(aceCount, diff / 10);
        isSoft = elevenAces > 0;
        numHand += elevenAces * 10;
    }

    /**
     * Get the total number of the player/dealer’s hand cards.
     * Notice that ace could be either 1 or 11.  It depends on what cards
     * the player/dealer has in a specific situation.
     */
    public int getNumHand()
    {
        /*
         * 'numHand' should have already been computed every time
         * addCard() or emptyCard() is called.
         */
        return numHand;
    }

    public String getName()
    {
        return name;
    }

    public boolean isSoft()
    {
        return isSoft;
    }

    /**
     * Print all hand cards of the player/dealer.
     * If hideFirstHand is {@code true}, the dealer's first card is hidden,
     * so you could print "hidden" for the first card.
     * If hideFirstHand is {@code false}, print all cards for the
     * player/dealer.
     * Numbers like 11, 12, 13 should not be seen, print "J", "Q", "K"
     * instead; ace could be treated as 1 or 11, print "A(1)" for ace.
     */
    public void printHand(boolean hideFirstHand)
    {
        if (numCard < 1) return;
        Card firstCard = hand[0];
        if (hideFirstHand) System.out.print("hidden");
        else System.out.print(rankName(firstCard));
        for (int i = 1; i < numCard; ++i) {
            System.out.print(", ");
            System.out.print(rankName(hand[i]));
        }
        System.out.println();
    }

    private String rankName(Card card) {
        int rank = card.getNumber();
        return (rank == Card.ACE ? "A" : rank == Card.JACK ? "J" :
                rank == Card.QUEEN ? "Q" : rank == Card.KING ? "K" :
                Integer.toString(rank));
    }

    public Card[] getHand()
    {
        Card[] hand = new Card[numCard];
        for (int i = 0; i < numCard; ++i) {
            hand[i] = this.hand[i];
        }
        return hand;
    }
}
