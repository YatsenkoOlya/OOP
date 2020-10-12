package com.company;

import java.util.List;

public class GameService {

    public void initGameWithCards(Game g) {

    }

    public void initGameWithPlayers(Game g, int playersCount) {

    }

    public void play(Game g) {
        //deal cards
        while (!isDeckEmpty(g) || isGameAlive(g)) {

            getSourcePlayer();
            getTarfgetPlayer();
            Round r = new Round();

            getFirstCard();
            Fight f = buildFight();

        }
    }

    private boolean isGameAlive(Game g) {

    }

    private boolean isPlayerAlive(Player p, Game g) {
        return true;
    }

    private boolean isDeckEmpty(Game g) {
        return true;
    }
}
