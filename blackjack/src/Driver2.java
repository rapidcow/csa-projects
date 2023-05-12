import java.util.Scanner;
import blackjack.Card;
import blackjack.Deck;
import blackjack.Player;
import blackjack.Suit;

public class Driver2
{
    public static void main(String[] args)
    {
        Deck myDeck = new Deck();

        // System.out.println("After initialization: ");
        // myDeck.writeDeck();
        // System.out.println();

        myDeck.shuffle();

        // System.out.println("After shuffle: ");
        // myDeck.writeDeck();
        // System.out.println();

        System.out.println("********************************************");
        System.out.println("******** BLACKJACK GAME STARTS NOW! ********");
        System.out.println("********************************************");

        Scanner sc = new Scanner(System.in);
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");

        // give each one two cards
        player.addCard(myDeck.dealNextCard());
        dealer.addCard(myDeck.dealNextCard());
        boolean playerDone = player.addCard(myDeck.dealNextCard());
        boolean dealerDone = dealer.addCard(myDeck.dealNextCard());

        System.out.print("Player: ");
        player.printHand(false);
        System.out.print("Dealer: ");
        dealer.printHand(true);
        System.out.println();

        String input = "";

        /*
         * Use while loop to assign a card to player and dealer if any
         * of them hits.  Get out of the loop when both of them stay.
         */
        while (!(playerDone && dealerDone)) {
            if (!playerDone) {
                while (true) {
                    System.out.print("Player (hit | stand): ");
                    input = sc.nextLine().trim().toLowerCase();
                    if (input.equals("stand")) {
                        playerDone = true;
                        break;
                    }
                    else if (input.equals("hit")) {
                        Card nextCard = myDeck.dealNextCard();
                        System.out.println("  drew " + formatCard(nextCard));
                        player.addCard(nextCard);
                        break;
                    }
                }
                // if (player.getNumHand() > Card.BLACKJACK) {
                //     System.out.println("  player > " + Card.BLACKJACK);
                //     break;
                // }
            }

            if (!dealerDone) {
                while (true) {
                    System.out.print("Dealer (hit | stand): ");
                    input = sc.nextLine().trim().toLowerCase();
                    if (input.equals("stand")) {
                        dealerDone = true;
                        break;
                    }
                    else if (input.equals("hit")) {
                        Card nextCard = myDeck.dealNextCard();
                        System.out.println("  drew " + formatCard(nextCard));
                        dealer.addCard(nextCard);
                        break;
                    }
                }
                if (player.getNumHand() > Card.BLACKJACK) {
                    System.out.println("  player > " + Card.BLACKJACK);
                    break;
                }
                if (dealer.getNumHand() > Card.BLACKJACK) {
                    System.out.println("  dealer > " + Card.BLACKJACK);
                    break;
                }
            }
        }

        sc.close();

        System.out.println();
        System.out.println("******** FINAL RESULT OF THE GAME: ********");
        player.printHand(false);
        System.out.println("-".repeat(32));
        dealer.printHand(false);
        System.out.println("-".repeat(32));

        int playerSum = player.getNumHand();
        int dealerSum = dealer.getNumHand();
        String playerSoft = player.isSoft() ? " (soft)" : "";
        String dealerSoft = dealer.isSoft() ? " (soft)" : "";
        System.out.println("player's total: " + playerSum + playerSoft);
        System.out.println("dealer's total: " + dealerSum + dealerSoft);

        /*
         * Conditionals to compare and display the winner
         */

        int game;
        int playerScore = player.getNumHand();
        int dealerScore = dealer.getNumHand();
        if (playerScore > Card.BLACKJACK)      game = -1;
        else if (dealerScore > Card.BLACKJACK) game = 1;
        else game = playerScore - dealerScore;
        if (game > 0) System.out.println("player wins");
        else if (game < 1) System.out.println("dealer wins");
        else System.out.println("tie");
    }

    private static String formatCard(Card c) {
        int rank = c.getNumber();
        String rankName = (rank == Card.ACE ? "A" :
                           rank == Card.JACK ? "J" :
                           rank == Card.QUEEN ? "Q" :
                           rank == Card.KING ? "K" :
                           Integer.toString(rank));
        return rankName + " of " + c.getSuit().getName();
    }
}
