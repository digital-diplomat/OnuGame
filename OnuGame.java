
// OnuGame.java: A Command-Line UNOⓇ clone.

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

/**
 * OnuGame: An UNO clone for the command line.
 * @author Vaughn Coates
 * @author Brennon Miller
 */
public class OnuGame {

    private static Card currentCard; // Card that was played last.
    private static AList<Card> deck = new AList<>(108); // our working deck
    private static AList<Card> discard = new AList<>(108); // deck of already played cards

    // Our players and their hands
    private static LList<Card> player1 = new LList<>();
    private static LList<Card> player2 = new LList<>();
    private static LList<Card> player3 = new LList<>();

    private static int playerIndex = 0; // Index of current player.
    private static int turnMod = 1; // Player order modifier.
    private static AList<LList<Card>> playerOrder = new AList<LList<Card>>(); // our list of players

    static int currentColor;
    static Scanner pInput = new Scanner(System.in); // Player input scanner.
    private OnuGame() {
    } // Private constructor prevents instantiation.

    /**
     * Main program.
     */
    public static void main(String[] args) {
        try {
            File intro = new File("title.txt");
            Scanner introWriter = new Scanner(intro);
            while (introWriter.hasNextLine()) {
                System.out.println(introWriter.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("The title screen file is missing. " 
                    + "Here's something generic instead.\n");
            System.out.println("Welcome to ONU. Press Enter to begin.");
        }

        // Player's card choice
        int choice = -1;

        //A menu interface here
        pInput.nextLine();

        // Add our players
        playerOrder.add(player1);
        playerOrder.add(player2);
        playerOrder.add(player3);

        // Create new deck and shuffle it.
        discard = newDeck();
        //testLoop(deck)
        //System.out.println("Shuffling...");
        deck = shuffleFrom(discard);
        //testLoop(deck)

        // Deal out cards
        for (int i = 0; i < 7; i++) {
            player1.add(deck.remove(0));
            player2.add(deck.remove(0));
            player3.add(deck.remove(0));
        }
        // Deal first card into play
        currentCard = deck.remove(0);
        currentColor = currentCard.color;

        // Main game loop
        while (true) {
            // Display current card
            System.out.println("\nCurrent card is " + currentCard.toString());

            // Pre-Turn Conditions
            if (currentCard.isDrawTwo()) { // Give two cards to current player.
                // Check if there are enough cards in the deck, if not refill it
                refillDeck(2);
                drawCards(2, deck, getCurrentPlayer());
                System.out.println("Player " + (playerIndex + 1) + ", draw 2.");
            }
            if (currentCard.isDrawFour()) { // Give four cards to current player.
                // Check deck again
                refillDeck(4);
                drawCards(4, deck, getCurrentPlayer());
                System.out.println("Player " + (playerIndex + 1) + ", draw 4.");
            }
            if (currentCard.isSkip() || currentCard.isDrawTwo() || currentCard.isDrawFour()) {
                System.out.println("Player " + (playerIndex + 1) + "'s turn ends.");
                playerIndex += turnMod; // Skip to next player.
                playerIndex %= playerOrder.size();
            }

            if (currentCard.isWild()) { // Wild color selection
                System.out.print("Last player chose ");
                switch (currentColor) {
                case Card.RED:
                    System.out.println("RED.");
                    break;
                case Card.YELLOW:
                    System.out.println("YELLOW.");
                    break;
                case Card.GREEN:
                    System.out.println("GREEN.");
                    break;
                case Card.BLUE:
                    System.out.println("BLUE.");
                    break;
                }
            }

            /*
                Display current player's hand and let them choose a card to play,
                play that card
                startagain
             */
            System.out.println("\n========================\n");
            System.out.println("\nPlayer " + (playerIndex + 1) + ", your turn.");
            System.out.println();
            //System.out.println("DEBUG: playerIndex = " + playerIndex);
            System.out.println("-1. [Draw a new card.]");
            for (int i = 0; i < getCurrentPlayer().size(); i++) {
                //display a menu of possible choices for the player
                System.out.println(i + ". " + getCurrentPlayer().get(i).toString());
            }
            //get player's choice.

            while (true) {
                System.out.print("> ");
                try {
                    choice = pInput.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Please input a number.\n> ");
                    pInput.nextLine();
                    continue;
                }
                if (choice < 0) {
                    //player doesn't have a card they need, 
                    //check that we can draw a card, if not, refill deck
                    refillDeck(1);
                    drawCards(1, deck, getCurrentPlayer());
                    System.out.println("Player " + (playerIndex + 1) + " draws a card.");
                    break;
                }
                try {
                    //check if player's selection is valid
                    if (getCurrentPlayer().get(choice).color == currentColor
                            || getCurrentPlayer().get(choice).value == currentCard.value
                            || getCurrentPlayer().get(choice).color == Card.WILD) {

                        //set new current card
                        currentCard = getCurrentPlayer().get(choice);

                        //change the current color of the game if necessary
                        if (currentColor != currentCard.color) {
                            currentColor = currentCard.color;
                        }
                        //check if card is wild, if so, make them choose a color
                        if (currentColor == Card.WILD) {
                            System.out.print("Red (1), Yellow (2), Green (3) or Blue(4)?\n> ");
                            currentColor = pInput.nextInt();
                        }
                        if (currentCard.isReverse()) {
                            System.out.println("Play reversed!");
                            turnMod = (turnMod == 1 ? (playerOrder.size() - 1) : 1);
                        }

                        //remove card from player's deck, add it to discard deck
                        discard.add(getCurrentPlayer().remove(choice));
                        break;
                    } else {
                        System.out.print("Card does not match!\n> ");
                    }
                } catch (NoSuchElementException e) {
                    System.out.print("Invalid choice!\n> ");
                }
            }

            if (getCurrentPlayer().size() == 1) {
                System.out.println("You have one card remaining!"); // UNO!
            } else if (getCurrentPlayer().size() < 1) {
                System.out.println("You win! Tell your friends!"); // Win cond.
                break;
            }
            playerIndex += turnMod; // Next player's turn.
            playerIndex %= playerOrder.size(); // Prevent overflow.
        } //End of main game loop

        //TODO: Implement saving past and present winners to persistent files!
        //TODO: Find out how to do this without getting an aneurysm 

        pInput.close();
    }//End of main
    /**
    * When a player wins, lets them enter their name and saves their number of wins to a file
    */
    public static void saveWinner(){
        //so we can read in records
        final String winFile = "winners.txt";
        String playerName;
        int wins = 0;
        //get winner's name
        System.out.println("What is your name? ");
        playerName = pInput.nextLine();
        System.out.println("Congratulations, " + playerName + "!");

        File file = new File(winFile);
        try{
            //open the win file, and look for the winner if they exist
            //Scanner sc = new Scanner(winFile);
            PrintWriter writer;
            // while(sc.hasNextLine()) {
            //     //find if the winner is already in our records
            //     if(sc.next().equals(playerName)){
            //         //we found our winner
            //         /*TODO: update wins or append a date of victory...or both,
            //         * this should invovle reading the file line by line and re-writing a new temp file with updated data
            //         * We should then delete the old file, and replace it with the new one
            //         */
            //         System.out.println("found " + playerName);
            //         break;
            //     }
            // 
            // }
            //if we et here, our winner wasn't found, add them to the records
            //TODO: add new winner
            writer = new PrintWriter(winFile);
            Date now = new Date();
            writer.print(playerName + " - " + now.toString() + "\n");
            writer.close();
            
            
        }
        catch(Exception ex){
            System.out.println(winFile + " Error saving file.");
            System.out.println(ex);
        }
        //close the file, return
        
        
        System.out.println("Win saved.");
    }
    // ======== Helper Functions Below ========

    /**
    * Checks number of cards in deck, if deck does not have enough cards,
    * reshuffle and refill it
    * @param cards the number of cards to check for
    */
    public static void refillDeck(int cards) {
        // If the deck doesn't have the amount of cards we need...
        if (deck.size() < cards) {
            for (int i = deck.size(); i >= 0; i--) {
                // ...remove the remaining cards to our discard deck
                discard.add(deck.remove(0));
            }
            //reshuffle the deck
            deck = shuffleFrom(discard);
            System.out.println("\n(Discard reshuffled into deck.)");
        }
    }

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
