package bjgame;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.Arrays;
import java.util.logging.Logger;

import blackjack.Card;
import blackjack.Suit;
import blackjack.Player;


public class CardHand extends JPanel
{
    private Player player;
    private Player playerWithoutFirstCard;
    /*
     * There's a very small possibility that we get 8 cards, but um...
     * let's not think about that now.  That would make everything
     * more complicated than needed.
     */
    private static final int NUM_CARDS = 6;
    private JLabel[] cardIcons;
    private int cardCount;
    private boolean hideFirstCard;
    private static Logger logger = Logger.getLogger("bjgame");

    public CardHand(String name)
    {
        setLayout(new GridLayout(0, NUM_CARDS, 2, 0));
        player = new Player(name);
        playerWithoutFirstCard = new Player(name);
        cardIcons = new JLabel[NUM_CARDS];
        cardCount = 0;
    }

    public void hideFirstCard()
    {
        hideFirstCard = true;
    }

    public void revealFirstCard()
    {
        hideFirstCard = false;
        Card[] currCards = player.getHand();
        clear();
        for (Card card : currCards) addCard(card);
    }

    public void clear()
    {
        player.emptyHand();
        playerWithoutFirstCard.emptyHand();
        while (cardCount > 0) {
            JLabel icon = cardIcons[--cardCount];
            remove(icon);
            cardIcons[cardCount] = null;
        }
        revalidate();
    }

    public boolean addCard(Card card)
    {
        boolean isOverTwentyOne = player.addCard(card);
        JLabel cardIcon;
        if (cardCount == 0 && hideFirstCard) {
            cardIcon = getCardBackSideIcon();
        }
        else {
            cardIcon = getCardIcon(card);
            playerWithoutFirstCard.addCard(card);
        }
        add(cardIcon);
        cardIcons[cardCount] = cardIcon;
        ++cardCount;
        revalidate();
        return isOverTwentyOne;
    }

    private JLabel getCardIcon(Card card) // throws IllegalArgumentException
    {
        String fileName = card.getSuit() + "_" + card.getNumber() + ".png";
        String filePath = "images/" + fileName;
        ImageIcon icon = createImage(filePath);
        JLabel cardIcon = new JLabel(icon);
        return cardIcon;
    }

    private JLabel getCardBackSideIcon() // throws IllegalArgumentException
    {
        ImageIcon icon = createImage("images/SUS.png");
        JLabel cardIcon = new JLabel(icon);
        return cardIcon;
    }

    /* Here's a method that Java defines:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
     */
    private ImageIcon createImage(String filePath)
        throws IllegalArgumentException
    {
        java.net.URL imageURL = getClass().getResource(filePath);
        if (imageURL == null) {
            String msg = "Path " + filePath + " not found";
            throw new IllegalArgumentException(msg);
        }
        Image scaledImage = (new ImageIcon(imageURL)
            .getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH));
        return new ImageIcon(scaledImage);
    }

    public int getNumHand()
    {
        logger.info("this is " + player.getName()
                    + " and i'm only accounting for "
                    + Arrays.toString(playerWithoutFirstCard.getHand()));
        logger.info("the num hand is "
                    + playerWithoutFirstCard.getNumHand());
        int numHand = playerWithoutFirstCard.getNumHand();
        /*
         * If the dealer's cards EXCLUDING the first hidden card add up
         * to a SOFT 21, we CANNOT call it a win for the dealer.  But again
         * this is possible because Player::getNumHand() considers any
         * score no greater than 21 valid.
         *
         * 21 is the only special case we have to deal with since the value
         * can never get over 21 (unless dealer has bust) and any value
         * below that is fine.
         */
        return numHand == 21 && hideFirstCard ? 11 : numHand;
    }

    /* don't reveal whether it is soft if the cards are hidden */
    public boolean isSoft()
    {
        return !hideFirstCard && player.isSoft();
    }
}
