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
}