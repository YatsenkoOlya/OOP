package com.company;

public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    private Hand hand;

    public Hand getHand() {
        return hand;
    }

    public Card selectCardToHit() {
        return card;
    }

    public Card selectCardToDefend() {
        return card;
    }

    public boolean isThrow() {

    }
}
