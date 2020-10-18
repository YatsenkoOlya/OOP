package com.company;

public class Main {

    public static void main(String[] args) {
        Game g = new Game();
        GameService s = new GameService();
        s.addCardsInGame(g);
        s.addPlayersInGame(g, 3);
        s.play(g);
    }
}
