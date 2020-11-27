package course2.oop;

import course2.oop.models.Game;
import course2.oop.service.GameService;

public class Main {

    public static void main(String[] args) {
        Game g = new Game();
        GameService s = new GameService();
        s.addCardsInGame(g);
        s.addPlayersInGame(g, 3);
        s.play(g);
    }
}
