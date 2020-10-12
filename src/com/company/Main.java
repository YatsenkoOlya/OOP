package com.company;

public class Main {

    public static void main(String[] args) {
        Game g = new Game();
        GameService svc = new GameService();
        svc.initGameWithCards(g);
        svc.initGameWithPlayers(g, 6);
        svc.play(g);
    }
}
