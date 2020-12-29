package course2.oop.models;

public enum Rank {
    SIX("6", 6), SEVEN("7", 7), EIGHT("8", 8), NINE("9", 9),
    TEN("10", 10), JACK("J", 11), QUEEN("Q", 12), KING("K", 13),
    ACE("A", 14);

    private int rank;
    private String name;

    public int getRank() {
        return rank;
    }

    Rank(String name, int rank) {
        this.rank = rank;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
