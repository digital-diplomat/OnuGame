
// OnuGame.java: A Command-Line UNOⓇ clone.

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * OnuGame: An UNO clone for the command line.
 * @author Vaughn Coates
 * @author Brennon Miller
 */
public class OnuGame {

    private static Card currentCard; // Card that was played last.
    private static AList<Card> deck = new AList<>(108); //our working deck
    private static AList<Card> discard = new AList<>(108); //deck of already played cards
    //Our players and their decks
    private static LList<Card> player1 = new LList<>();
    private static LList<Card> player2 = new LList<>();
    private static LList<Card> player3 = new LList<>();

    private static int playerIndex = 0; // Index of current player.
    private static int turnMod = 1; // Player order modifier.
    private static AList<LList<Card>> playerOrder = new AList<LList<Card>>(); //our list of players

    private OnuGame() {
    } // Private constructor prevents instantiation.

    /**
     * Main program loop.
     */
    public static void main(String[] args) {

        int choice = -1;

        playerOrder.add(player1);
        playerOrder.add(player2);
        playerOrder.add(player3);

        Scanner pInput = new Scanner(System.in);

        // Create new deck and shuffle it.
        discard = newDeck();
        //      testLoop(deck)
        //      System.out.println("Shuffling...");
        deck = shuffleFrom(discard);
        //      testLoop(deck)

        // Deal out cards
        for (int i = 0; i < 7; i++) {
            player1.add(deck.remove(0));
            player2.add(deck.remove(0));
            player3.add(deck.remove(0));
        }
        // Deal first card into play.
        currentCard = deck.remove(0);

        // Main game loop
        while (true) {

            // Pre-Turn Conditions
            if (currentCard.isDrawTwo()) {
                if (deck.size() < 2) {
                    for (int i = deck.size(); i >= 0; i--) {
                        discard.add(deck.remove(0));
                    }
                    deck = shuffleFrom(discard);
                }
                drawCards(2, deck, getCurrentPlayer());
            }
            if (currentCard.isDrawFour()) {
                if (deck.size() < 4) {
                    for (int i = deck.size(); i >= 0; i--) {
                        discard.add(deck.remove(0));
                    }
                    deck = shuffleFrom(discard);
                }
                drawCards(4, deck, getCurrentPlayer());
            }
            if (currentCard.isSkip() || currentCard.isDrawTwo() || currentCard.isDrawFour()) {
                playerIndex += turnMod; // Skip to next player.
                playerIndex %= playerOrder.size();
            }
            if (currentCard.isReverse()) {
                turnMod = (turnMod == 1 ? (playerOrder.size() - 1) : 1);
            }

            // TODO: Have current player put a card onto the stack, or draw a
            // card if unable to do so.
            /*
                Display current player's hand and let them choose a card to play,
                play that card
                startagain
             */
            for (int i = 0; i < getCurrentPlayer().size(); i++) {
                //display a menu of possible choices for the player
                System.out.println(i + ". " + getCurrentPlayer().get(i).toString());
            }
            //get players choice.

            //TODO: implement a trap for invalid choices? Let's not have people break out stuff.
            do {
                choice = pInput.nextInt();
                try {
                    //set new current card
                    currentCard = getCurrentPlayer().get(choice);
                    //remove card from player's deck, add it to discard deck
                    discard.add(getCurrentPlayer().remove(choice));
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid choice!");
                }
            } while (choice < 0 || choice > i);

            if (getCurrentPlayer().size() == 1) {
                System.out.println("You have one card remaining!");
            }
            if (getCurrentPlayer().size() < 1) {
                System.out.println("You win! Tell your friends!");
                break;
            }
        }
        pInput.close();
    }

    // ======== Helper Functions Below ========

    /**
     * Pulls random cards from the old deck until the old deck runs out,
     * returning a deck in which these cards are placed in random order.
     *
     * Known issue: calling on a deck without returning to a new deck deletes
     * the deck permanently.
     *
     * @param fromDeck
     */
    private static AList<Card> shuffleFrom(AList<Card> fromDeck) {
        //our new deck and random Object
        AList<Card> newDeck = new AList<>();
        Random rand = new Random();

        for (int maxIndex = fromDeck.size(); maxIndex > 0; maxIndex--) {
            newDeck.add(fromDeck.remove(Math.abs(rand.nextInt()) % maxIndex));
        }
        return newDeck;
    }

    /**
     * Gets the current player's hand
     * @param players
     * @param playerIndex
     * @return current player's hand
     *
     */
    private static LList<Card> getCurrentPlayer() {
        return (LList<Card>) playerOrder.get(playerIndex);
    }

    /**
     *Generates new deck in color/value order; will need to be shuffled.
     * @return new Deck of 108 cards with each type necessary for game function
     */
    private static AList<Card> newDeck() {
        int[] values = { 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, Card.SKIP, Card.SKIP, Card.REVERSE,
                Card.REVERSE, Card.DRAW, Card.DRAW };

        int[] colors = { Card.RED, Card.YELLOW, Card.GREEN, Card.BLUE };

        AList<Card> deck = new AList<>();

        // Add color cards.
        for (int c : colors) {
            for (int v : values) {
                deck.add(new Card(c, v));
            }
        }

        // Add wildcards
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(Card.WILD, 0));
            deck.add(new Card(Card.WILD, Card.DRAW));
        }
        return deck;
    }

    /**
     * Draw x number of cards from `from` to a given `to` hand.
     * @param cardCount
     * @param from
     * @param to
     */
    private static void drawCards(int cardCount, List<Card> from, List<Card> to) {
        for (int i = 0; i < cardCount; i++) {
            to.add(from.remove(0));
        }
    }
}
