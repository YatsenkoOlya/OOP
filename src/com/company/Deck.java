package com.company;

import java.util.ArrayList;

public class Deck { // колода
    private Pile pile;
    private ArrayList<Card> usedCards;
    private ArrayList<Card> cardsOnDeck;

    public Deck() {
        this.pile = new Pile();
        this.usedCards=new ArrayList<Card>();
        this.cardsOnDeck = new ArrayList<Card>();
    }

    public void addCardOnDeck(Card card) {
        cardsOnDeck.add(card);
    }

    public void addCardsToUsedCards() {
        usedCards.addAll(cardsOnDeck);
        cardsOnDeck=null;
    }
}