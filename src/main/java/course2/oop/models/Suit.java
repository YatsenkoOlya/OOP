package course2.oop.models;

public enum Suit {

    CLUBS((char) 27 + "[30m♣" + (char) 27 + "[0m"),
    DIAMONDS((char) 27 + "[31m♦" + (char) 27 + "[0m"),
    HEARTS((char) 27 + "[31m♥" + (char) 27 + "[0m"),
    SPADES((char) 27 + "[30m♠" + (char) 27 + "[0m");

    private String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return suit;
    }
}
