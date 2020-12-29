package course2.oop;

import course2.oop.models.Game;
import course2.oop.service.GameService;
import course2.oop.service.SerializeService;

public class Main {

    public static void main(String[] args) {
        Game g = new Game();
        GameService s = new GameService();
        s.addCardsInGame(g);
        s.addPlayersInGame(g, 3);
        s.play(g);
        SerializeService se = new SerializeService();
        se.serialize(g);
        g = se.deserialize();
    }
}
