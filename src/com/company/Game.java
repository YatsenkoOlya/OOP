package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private Suit trump;
    private List<Player> players;
    private List<Card> cars;
    private Map<Player, List<Card>> playerToCardsMap;
    private List<Round> stepList;
}
