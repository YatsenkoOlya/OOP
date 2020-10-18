package com.company;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {
    private Card trump; // козырь
    private List<Player> players;
    private Stack<Card> cards; // колода
    private Map<Player, List<Card>> playersCards; // карты игрока

    public Map<Player, List<Card>> getPlayersCards() {
        return playersCards;
    }

    public void setPlayersCards(Map<Player, List<Card>> playersCards) {
        this.playersCards = playersCards;
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Card getTrump() {
        return trump;
    }

    public void setTrump(Card trump) {
        this.trump = trump;
    }
}
