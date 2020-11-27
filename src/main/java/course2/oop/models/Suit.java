package course2.oop.models;

public enum Suit {

    CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");

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
