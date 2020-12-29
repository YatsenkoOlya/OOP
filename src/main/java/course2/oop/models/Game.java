package course2.oop.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {
    private Card trumpCard; // козырная карта (которая лежит во главе колоды)
    private List<Player> players;
    private Stack<Card> cards;  // колода
    private Map<Player, List<Card>> playersCards; // карты игрока
    private List<Round> rounds; // раунды

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

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void addRound(Round round) {
        if (rounds == null) {
            rounds = new ArrayList();
        }
        rounds.add(round);
    }
}
