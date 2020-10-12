package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import static com.company.Card.RANKS;
import static com.company.Card.SUIT;

public class Pile {
    private Stack<Card> cards;
    public Pile() {
        createPile();
    }

    private void createPile() {
        cards = new Stack<Card>();

        ArrayList<Card> allCards = new ArrayList<Card>();
        for (String rank : RANKS) {
            for (String suit : SUIT) {
                allCards.add(new Card(rank, suit));
                Collections.shuffle(allCards);
            }
        }

        for (Card card : allCards) {
            cards.push(card);
        }
    }

    public boolean isEmpty() {
        return cards.empty();
    }

    public Stack<Card> getCards(){
        return cards;
    }

    public Card draw() {
        if (!isEmpty()) {
            return cards.pop();
        } else {
            return null;
        }
    }
}
