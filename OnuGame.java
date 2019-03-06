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
        // Deal first card.
        currentCard = deck.remove(0);

        // TODO: Implement game loop.

    }

// ======== Helper Functions Below ========

    /**
     * Pulls random cards from the old deck until the old deck runs out,
     * returning a deck in which these cards are placed in random order.
     * 
     * Known issue: calling on a deck without returning to a new deck deletes
     * the deck permanently.
     * @param fromDeck deck to pull from
     * @return deck with items shuffled.
     */
    private static AList<Card> shuffleFrom(AList<Card> fromDeck) {
        //our new deck and random Object
        AList<Card> newDeck = new AList<>();
        Random rand = new Random();
        
        for(int maxIndex = 108; maxIndex > 0; maxIndex--){
            newDeck.add(fromDeck.remove(Math.abs(rand.nextInt()) % maxIndex));
        }
        return newDeck;
    }

    /**
     * Generates new deck in color/value order; will need to be shuffled.
     * @return new ordered deck (List) of cards.
     */
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

    /**
     * 
     */
    private static void testLoop(AList<Card> deck) {
        for (Card c : deck) {
            System.out.print(c.color);
            System.out.print(" / ");
            System.out.print(c.value);
            System.out.println();
         }
    }
}