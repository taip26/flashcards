package gourd.flashcards;

/**
 * Class that represents a single card in a set
 */
public class Card {
    private final int id;
    private String front;
    private String back;
    private boolean flipped;
    private final int setId;

    /**
     * Card constructor
     *
     * @param id Unique ID of the card
     * @param setId ID of set that this card belongs to
     */
    public Card(int id, int setId) {
        this.id = id;
        this.setId = setId;
        this.flipped = false;
    }

    /**
     * Card constructor with optional front or back input
     *
     * @param id Unique ID of the card
     * @param setId ID of set that this card belongs to
     */
    public Card(int id, int setId, String front, String back) {
        this.id = id;
        this.setId = setId;
        this.front = front;
        this.back = back;
        this.flipped = false;
    }

    /**
     * Sets the front side of the card
     *
     * @param in Text or image path of card content
     */
    public void setFront(String in) {
        front = in;
    }

    /**
     * Sets the back side of the card
     *
     * @param in Text or image path of card content
     */
    public void setBack(String in) {
        back = in;
    }

    /**
     * Getter method for card id
     *
     * @return This card's id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter method for front
     *
     * @return Front content
     */
    public String getFront() {
        return front;
    }

    /**
     * Getter method for back
     *
     * @return Back content
     */
    public String getBack() {
        return back;
    }

    public int getSetId() {
        return setId;
    }

    /**
     * Displays the current face of card
     *
     * @return Current card face
     */
    public String show() {
        if (this.flipped) {
            return this.back;
        } else {
            return this.front;
        }
    }

    /**
     * Flips card, then returns the current side
     *
     * @return Current side after flip
     */
    public String flip() {
        this.flipped ^= true;
        if (this.flipped) {
            return this.back;
        } else {
            return this.front;
        }
    }

    /**
     * Equals method for Card; two cards are equal if their front and back strings are equal
     *
     * @param obj Card that this Card is being compared to
     * @return True if the cards are equal and false if they are not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof Card that) {
            return front.equals(that.front) &&
                    back.equals(that.back);
        }
        return false;
    }

    /**
     * String representation for a Card. A Card is represented in the form
     * [<FRONT>|<BACK>] id:<ID> s_id:<SETID>
     * if there is no front or back, then the card representation is
     * [] id:<ID> s_id:<SETID>
     * if there is only one front or back, then the card representation is
     * [<FRONT>|] id:<ID> s_id:<SETID> or [|<BACK>] id:<ID> s_id:<SETID>
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (front != null) {
            sb.append(front);
        }
        if (back != null) {
            sb.append("|");
            sb.append(back);
        }
        sb.append("] id:");
        sb.append(id);
        sb.append(" s_id:");
        sb.append(setId);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return 17 * front.hashCode() ^ back.hashCode();
    }
}