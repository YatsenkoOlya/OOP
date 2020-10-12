package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card {
    private String rank;
    private String suit; // масть

    public Card(String r, String s) {
        if (Arrays.asList(RANKS).contains(r) && Arrays.asList(SUIT).contains(s)) {
            rank = r;
            suit = s;
        }
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public static final String[] RANKS = {
            "6",
            "7",
            "8",
            "9",
            "10",
            "Jack",
            "Queen",
            "King",
            "Ace"
    };

    public static final Map<String, Integer> values;
    static {
        Map<String, Integer> valuesMap = new HashMap<String, Integer>();
        valuesMap.put("6", 6);
        valuesMap.put("7", 7);
        valuesMap.put("8", 8);
        valuesMap.put("9", 9);
        valuesMap.put("10", 10);
        valuesMap.put("Jack", 11);
        valuesMap.put("Queen", 12);
        valuesMap.put("King", 13);
        valuesMap.put("Ace", 14);
        values = Collections.unmodifiableMap(valuesMap);
    }

    public static final String[] SUIT = {
            "Hearts",
            "Diamonds",
            "Clubs",
            "Spades"
    };
}