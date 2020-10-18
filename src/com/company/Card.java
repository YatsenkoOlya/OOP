package com.company;

public class Card implements Comparable<Card>{
    private Rank rank;
    private Suit suit;

    public Card(Rank r, Suit s) {
        rank = r;
        suit = s;
    }

    public Rank getRank(){
        return rank;
    }
    public Suit getSuit(){
        return suit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rank.toString());
        sb.append(suit.toString());
        return sb.toString();
    }

    @Override
    public int compareTo(Card second) {
        return this.rank.getRank()-second.rank.getRank();
    }
}