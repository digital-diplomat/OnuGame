/**
 * The base element of the text-based ONU card game.
 * @author Brennon Miller
 * @author Vaugh Coates
 */

public class Card {
    // It's safe to make these public because they're immutable (final).
    public final int value;
    public final int color;

    // Use color names in place of numbers for readability
    final static int RED = 1;
    final static int YELLOW = 2;
    final static int GREEN = 3;
    final static int BLUE = 4;
    final static int WILD = 0;
    
    // Same for special value names.
    final static int SKIP = 10;
    final static int REVERSE = 11;
    final static int DRAW = 12;

    /**
     * Constructor
     * Cards are pulled from a shuffled deck, NOT randomly generated.
     * So, the constructor is not randomized; rather, a loop will determine how
     * many of each card is added.
     */
    public Card(int col, int val) {
        /**
         * For normal cards, the number on the card.
         * For special cards, encodes the card's function.
         * DRAW attribute is used both for Wild Draw 4 and Draw 2 cards.
         */
        this.value = val;
        /**
         * Indicates what color the card has, or if it is WILD.
         */
        this.color = col;
    }

    /**
     * Check for skip card.
     * @return true if card skips next player.
     */
    public boolean isSkip() {
        return (value == SKIP);
    }

    /**
     * Check for reverse card.
     * @return true if card reverses play order.
     */
    public boolean isReverse() {
        return (value == REVERSE);
    }

    /**
     * Check for draw-two card.
     * @return true if card causes next player to draw two more cards.
     */
    public boolean isDrawTwo() {
        return (color != WILD && value == DRAW);
    }

    /**
     * Check for wild card.
     * @return true if card can be played on any other card, changing the play color.
     */
    public boolean isWild() {
        return (color == WILD);
    }

    /**
     * Check for wild draw four
     * @return true if card acts as a wild AND next player draws 4 cards.
     */
    public boolean isDrawFour() {
        return (color == WILD && value == DRAW);
    }

    @Override
    public String toString() {
        char smColor = ' '; // Symbol representing color, easy reading.
        String symbol = "";
        String colString = "";
        String valString = "";
        switch (this.color) {
            case Card.RED:
                colString = "Red";
                smColor = '*';
                break;
            case Card.YELLOW:
                colString = "Yellow";
                smColor = '#';
                break;
            case Card.GREEN:
                colString = "Green";
                smColor = '$';
                break;
            case Card.BLUE:
                colString = "Blue";
                smColor = '~';
                break;
            case Card.WILD:
                colString = "Wild";
                valString = "Card!";
                smColor = '?';
                symbol = "W!";
        }
        switch (this.value) {
            case Card.DRAW:
                if (this.isWild()) {
                    valString = "Draw 4";
                    symbol = "++";
                } else {
                    valString = "Draw 2";
                    symbol = "+";
                }
                break;
            case Card.SKIP:
                valString = "Skip";
                symbol = "S";
                break;
            case Card.REVERSE:
                valString = "Reverse";
                symbol = "R";
                break;
            default:
                if (this.color != Card.WILD) {
                    symbol = valString = ((Integer) this.value).toString();
                }
        }
        return smColor + symbol + smColor + " | " + colString + " " + valString;
    }
}