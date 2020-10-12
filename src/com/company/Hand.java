package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Hand {


    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public void removeCard(Card c) {
        cards.remove(c);
    }

    public int size() {
        return cards.size();
    }


    @Override
    public String toString() {
        String ret = new String();
        for (Card c : cards) {
            ret += c + "\n";
        }
        return ret;
    }

    public boolean needsToDraw() {
        if (size() > 6) {
            return true;
        } else {
            return false;
        }
    }

    public int numberToDraw() {
        if (needsToDraw()) {
            return 0;
        } else {
            return 6 - size();
        }
    }
}
