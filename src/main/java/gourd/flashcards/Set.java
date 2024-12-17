package gourd.flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that represents a set of cards
 */
public class Set {
    private final int id;
    private String name;
    private int pos; // position of iterator
    private int numCards;
    private List<Card> cards;

    /**
     * Set constructor
     *
     * @param id Unique set id
     * @param name Name of set
     */
    public Set(int id, String name) {
        this.id = id;
        this.name = name;
        this.cards = new ArrayList<Card>();
        this.pos = 0;
        this.numCards = 0;
    }

    public int getId() {
        return id;
    }

    public void setCardList(List<Card> cardList) {
        cards = cardList;
    }

    public List<Card> getCardList() {
        return cards;
    }

    /**
     * Creates a card and adds it to this set
     *
     * @param id Unique ID that represents the card
     * @param front Front content of card; may be null or ""
     * @param back Back content of card; may be null or ""
     */
    public void addCard(int id, String front, String back) {
        Card card = new Card(generateID(), id);
        card.setFront(front);
        card.setBack(back);
        this.cards.add(card);
        this.numCards++;
    }

    /**
     * Removes card with specified id
     *
     * @param cardId ID of card to be removed
     *
     * @return True if removal is successful, false otherwise
     */
    public boolean removeCard(int cardId) {
        for (Card card : cards) {
            if (card.getId() == cardId) {
                cards.remove(card);
                this.numCards--;
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a random ID
     */
    public static int generateID() {
        Random gen = new Random();
        return gen.nextInt(1048576);
    }

    /**
     * Getter method for name
     *
     * @return Name of set
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     *
     * @param name New name of set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the card at current iterator position
     *
     * @return Card at current position
     */
    public Card getCurrentCard() {
        return cards.get(pos);
    }

    /**
     * Advances the iterator one position, then returns the next card
     *
     * @return The next card
     */
    public Card getNextCard() {
        pos = (pos + 1) % numCards;
        return getCurrentCard();
    }

    /**
     * Retreats the iterator one position, then returns the previous card
     *
     * @return The previous card
     */
    public Card getPrevCard() {
        pos = (pos - 1 + numCards) % numCards;
        return getCurrentCard();
    }

    /**
     * Indicates whether this set's card list is empty
     *
     * @return True if the card list is empty, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}