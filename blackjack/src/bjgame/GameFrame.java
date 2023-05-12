package bjgame;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.util.logging.Logger;

import blackjack.Card;
import blackjack.Deck;

/*
 * A rough layout :L (in the MENU bar we'll have the RESET button)
 *
 *    //================================\\
 *    || +--------------+               ||
 *    || | Player cards |  Player total (e.g. 7 (soft))
 *    || +--------------+               ||
 *    || | Dealer cards |  Dealer total (e.g. 7)
 *    || +--------------+               ||
 *    || Hit Stand       Hit Stand      ||
 *    || +----------------------------+ ||
 *    || | Message board              | ||
 *    || +----------------------------+ ||
 *    \\================================//
 *
 *
 * RULES OF THE DUELS
 * ==================
 *
 *   *  Before the player STANDS, the dealer's first card faces down.
 *      The total does not take the hidden card into account.
 *   *  While the player HITS, update the total.  When it is 21, the
 *      player automatically wins the game, showing all dealer's cards.
 */

public class GameFrame extends JFrame
{
    private JPanel content;

    /*
     * things to keep track of including
     *   -  player card
     *   -  dealer card
     *   -  scores
     *   -  the console-like thing
     *   -  and the buttons, including
     *      *  hit / stand
     *      *  yes / no with play again
     */

    private Deck deck;
    private CardHand playerHand;
    private CardHand dealerHand;

    private JLabel playerScore;
    private JLabel dealerScore;
    private JLabel messageBoard;
    private StringBuffer message;   /* synchronized because message might
                                     * be changed in different threads
                                     * (on button click or something) */

    private JButton button1;        /* hit or restart */
    private JButton button2;        /* stand or exit */

    private boolean isPlayerTurn;
    private boolean isGameOver;

    private boolean playerDone;
    private boolean dealerDone;
    private boolean playerBust;
    private boolean dealerBust;
    private int playerNum;
    private int dealerNum;

    private int playerTScore;       /* player total score */
    private int dealerTScore;       /* dealer total score */

    private static Logger logger = Logger.getLogger("bjgame");

    public GameFrame()
    {
        super();
        setTitle("Blackjack");
        content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));
        setContentPane(content);

        playerHand = new CardHand("Player");
        dealerHand = new CardHand("Dealer");

        /* XXX: Now that I know JScrollPane is much more suited for this
         * job, I wonder why I can't set the minimum size for the JLabel
         * here... */
        // messageBoard = new JLabel() {
        //     @Override
        //     public Dimension getMinimumSize()
        //     {
        //         Dimension d = super.getMinimumSize();
        //         d.height = 100;
        //         return d;
        //     }
        // };
        messageBoard = new JLabel();
        messageBoard.setVerticalAlignment(SwingConstants.BOTTOM);
        message = new StringBuffer();

        playerScore = new JLabel("Player total");
        dealerScore = new JLabel("Dealer total");

        button1 = new JButton("Hit");
        button2 = new JButton("Stand");

        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                logger.info("the hit button has been depressed");
                if (isGameOver) {
                    logger.info("restart");
                    startGame();
                }
                else {
                    if (isPlayerTurn) {
                        logger.info("player hit");
                        Card card = deck.dealNextCard();
                        playerBust = playerHand.addCard(card);
                        addMessage("player drew " + formatCard(card));
                        checkGameWithoutEnding();
                    }
                    else {
                        logger.info("dealer hit");
                        Card card = deck.dealNextCard();
                        dealerBust = dealerHand.addCard(card);
                        addMessage("dealer drew " + formatCard(card));
                        checkGame();
                    }
                }
                revalidate();
            }
        });

        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                logger.info("the stan button has been depressed");
                if (isGameOver) {
                    logger.info("exit");
                    System.exit(0);
                }
                else {
                    if (isPlayerTurn) {
                        logger.info("player stand");
                        playerDone = true;
                        checkGame();
                    }
                    else {
                        logger.info("dealer stand");
                        dealerDone = true;
                        checkGame();
                    }
                }
                revalidate();
            }
        });

        isPlayerTurn = true;
        isGameOver = false;

        playerTScore = 0;
        dealerTScore = 0;
    }

    /** Put every component in their place */
    public void initialize()
    {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        // c.gridwidth = 1;
        // c.gridheight = 1;
        c.weightx = 3.0;
        c.weighty = 1.0;
        // c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 10, 0);
        // c.ipadx = 0;
        // c.ipady = 0;
        content.add(playerHand, c);
        c.gridy = 1;
        content.add(dealerHand, c);

        // total scores
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        // c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 2.5;
        // c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(0, 10, 0, 0);
        // c.ipadx = 0;
        // c.ipady = 0;
        content.add(playerScore, c);
        c.gridy = 1;
        content.add(dealerScore, c);

        // Screen stuff
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        // c.gridheight = 1;
        c.weightx = 1.5;
        // c.weighty = 0.0;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        // c.ipadx = 0;
        // c.ipady = 0;
        content.add(messageBoard, c);

        // Buttons
        c.gridx = 1;
        c.gridy = 2;
        // c.gridwidth = 1;
        // c.gridheight = 1;
        c.weightx = 0.5;
        // c.weighty = 1.5;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.fill = GridBagConstraints.NONE;
        // c.insets = new Insets(0, 0, 0, 0);
        // c.ipadx = 0;
        // c.ipady = 0;
        content.add(button1, c);
        c.gridx = 2;
        content.add(button2, c);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame()
    {
        playerHand.clear();
        dealerHand.clear();
        dealerHand.hideFirstCard();

        isGameOver = false;
        isPlayerTurn = true;
        playerDone = false;
        dealerDone = false;
        playerBust = false;
        dealerBust = false;
        deck = new Deck();
        deck.shuffle();
        playerHand.addCard(deck.dealNextCard());
        dealerHand.addCard(deck.dealNextCard());
        boolean playerBust = playerHand.addCard(deck.dealNextCard());
        boolean dealerBust = dealerHand.addCard(deck.dealNextCard());

        message.setLength(0);       /* clear the StringBuffer */
        messageBoard.setText("");
        button1.setText("Hit");
        button2.setText("Stand");
        updateScores();
        revalidate();
        if (playerBust || dealerBust) endGame();
    }

    public void display()
    {
        pack();
        setVisible(true);
    }

    private void checkGame()
    {
        logger.info("checking game");
        boolean bust = playerBust || dealerBust;

        /* Update the score counts */
        updateScores();
        if (bust || (playerDone && dealerDone)) endGame();
        logger.info("player done: " + playerDone);
        logger.info("dealer done: " + dealerDone);
        rotateTurn();
        logger.info("is player turn: " + isPlayerTurn);
    }

    private void checkGameWithoutEnding()
    {
        updateScores();
        rotateTurn();
    }

    private void rotateTurn()
    {
        if (isPlayerTurn) {
            if (!dealerDone) isPlayerTurn = false;
        }
        else {
            if (!playerDone) isPlayerTurn = true;
        }
    }

    private void endGame()
    {
        /* reveal everything */
        dealerHand.revealFirstCard();
        updateScores();

        /* same algorithm as Driver2.java */
        int game = 0;
        if (playerNum > Card.BLACKJACK) game = -1;
        else if (dealerNum > Card.BLACKJACK) game = 1;
        else game = playerNum - dealerNum;

        String message;
        if (game > 0) {
            playerTScore += 1;
            message = "Player wins!";
        }
        else if (game < 0) {
            dealerTScore += 1;
            message = "Dealer wins!";
        }
        else {
            message = "Tie.";
        }
        addMessage(String.format(
            "%s %d-%d", message, playerTScore, dealerTScore));

        isGameOver = true;
        button1.setText("Play Again");
        button2.setText("Exit");
        revalidate();
    }

    private void updateScores() {
        playerNum = playerHand.getNumHand();
        dealerNum = dealerHand.getNumHand();

        String text = "Player score: " + playerNum;
        if (playerHand.isSoft()) text += " (soft)";
        playerScore.setText(text);

        text = "Dealer score: " + dealerNum;
        if (dealerHand.isSoft()) text += " (soft)";
        dealerScore.setText(text);
        revalidate();
    }

    private void addMessage(String msg)
    {
        // if (message.length() != 0) message.append("<br/>");
        message.setLength(0);
        message.append(
            msg.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;"));
        messageBoard.setText("<html>" + message + "</html>");
        revalidate();
    }

    /* shabby functions */
    private String formatCard(Card c) {
        int rank = c.getNumber();
        String rankName = (rank == Card.ACE ? "ace" :
                           rank == Card.JACK ? "jack" :
                           rank == Card.QUEEN ? "queen" :
                           rank == Card.KING ? "king" :
                           Integer.toString(rank));
        String suitName = c.getSuit().getName();
        return rankName + " of " + suitName;
    }

    private boolean hasBust(CardHand h) {
        return h.getNumHand() >= Card.BLACKJACK;
    }
}
