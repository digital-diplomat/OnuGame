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
    public static void main() {
        List<Card> deck = new AList(108);
        List<Card> discard = new AList(108);

        List<Card> player1 = new LList();
        List<Card> player2 = new LList();
        List<Card> player3 = new LList();

        // TODO: Implement game loop.

    }

    /**
     * Pulls random cards from the old deck until the old deck runs out,
     * returning a deck in which these cards are placed in random order.
     * 
     * Known issue: calling on a deck without returning to a new deck deletes
     * the deck permanently.
     * @param fromDeck deck to pull from
     * @return deck with items shuffled.
     */
    private List<Card> shuffleFrom(List<Card> fromDeck) {
        // TODO: Implement
    }

    /**
     * Generates new deck in color/value order; will need to be shuffled.
     * @return new ordered deck (List) of cards.
     */
    private List<Card> newDeck() {
        int[] values = {0, 1, 1, 2, 2, 3, 3, 4, 4,
                        5, 5, 6, 6, 7, 7, 8, 8, 9, 9,
                        Card.SKIP, Card.SKIP, Card.REVERSE, Card.REVERSE,
                        Card.DRAW, Card.DRAW};

        int[] colors = {Card.RED, Card.YELLOW, Card.GREEN, Card.BLUE};

        List<Card> deck = new AList();

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
}