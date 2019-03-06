// OnuGame.java: A Command-Line UNOⓇ clone.

import java.util.Random;
import java.util.Scanner;

/**
 * OnuGame: An UNO clone for the command line.
 * @author Vaughn Coates
 * @author Brennon Miller
 */
public class OnuGame {

    private OnuGame() {}    // Private constructor prevents instantiation.

    /**
     * Main program loop.
     */
    public static void main(String[] args) {
        Card currentCard;   // Card that was played last.
        AList<Card> deck = new AList<>(108);
        AList<Card> discard = new AList<>(108);

        LList<Card> player1 = new LList<>();
        LList<Card> player2 = new LList<>();
        LList<Card> player3 = new LList<>();

        LinkedDeque<LList<Card>> playerOrder = new LinkedDeque<LList<Card>>();

        playerOrder.addLast(player1);
        playerOrder.addLast(player2);
        playerOrder.addLast(player3);

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
            if (currentCard.isDrawTwo()) {
                if (deck.size() < 2) {
                    for (int i = deck.size(); i >= 0; i--) {
                        discard.add(deck.remove(0));
                    }
                    deck = shuffleFrom(discard);
                }
                drawCards(2, deck, playerOrder.getFirst());
            }
            if (currentCard.isDrawFour()) {
                if (deck.size() < 4) {
                    for (int i = deck.size(); i >= 0; i--) {
                        discard.add(deck.remove(0));
                    }
                    deck = shuffleFrom(discard);
                }
                drawCards(4, deck, playerOrder.getFirst());
            }
            if (currentCard.isSkip() || currentCard.isDrawTwo() || currentCard.isDrawFour()) {
                nextTurn(playerOrder);
            }
            if (currentCard.isReverse()) {
                // TODO: Reverse player order.
            }

            // TODO: Have current player put a card onto the stack, or draw a
            // card if unable to do so.
        }

    }

// ======== Helper Functions Below ========

    /*
     * Pulls random cards from the old deck until the old deck runs out,
     * returning a deck in which these cards are placed in random order.
     * 
     * Known issue: calling on a deck without returning to a new deck deletes
     * the deck permanently.
     */
    private static AList<Card> shuffleFrom(AList<Card> fromDeck) {
        //our new deck and random Object
        AList<Card> newDeck = new AList<>();
        Random rand = new Random();
        
        for(int maxIndex = fromDeck.size(); maxIndex > 0; maxIndex--){
            newDeck.add(fromDeck.remove(Math.abs(rand.nextInt()) % maxIndex));
        }
        return newDeck;
    }

    
    // Generates new deck in color/value order; will need to be shuffled.
    private static AList<Card> newDeck() {
        int[] values = {0, 1, 1, 2, 2, 3, 3, 4, 4,
                        5, 5, 6, 6, 7, 7, 8, 8, 9, 9,
                        Card.SKIP, Card.SKIP, Card.REVERSE, Card.REVERSE,
                        Card.DRAW, Card.DRAW};

        int[] colors = {Card.RED, Card.YELLOW, Card.GREEN, Card.BLUE};

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

    /* DEBUG: Do not use.
    private static void testLoop(List<Card> deck) {
        for (Card c : deck) {
            System.out.print(c.color);
            System.out.print(" / ");
            System.out.print(c.value);
            System.out.println();
         }
    }
    */

    // Advance turn
    private static void nextTurn(LinkedDeque<LList<Card>> order) {
        order.addLast(order.removeLast());
    }

    // Draw x number of cards from `from` to a given `to` hand.
    private static void drawCards(int cardCount, List<Card> from, List<Card> to) {
        for (int i = 0; i < cardCount; i++) {
            to.add(from.remove(0));
        }
    }
}