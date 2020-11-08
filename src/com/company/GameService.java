package com.company;

import java.util.*;

public class GameService {

    public void addCardsInGame(Game g) {
        ArrayList<Card> allCards = new ArrayList<>();
        Stack<Card> cards = new Stack<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                allCards.add(new Card(rank, suit));
                Collections.shuffle(allCards);
            }
        }
        Card trump = allCards.remove(35);
        g.setTrump(trump);
        cards.push(trump);
        for (Card card : allCards) {
            cards.push(card);
        }
        g.setCards(cards);
    }

    public void addPlayersInGame(Game g, int playersCount) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playersCount; i++) {
            players.add(new Player(Integer.toString(i)));
        }
        g.setPlayers(players);
    }

    public void play(Game g) {
        dialCards(g);  //раздаем карты
        System.out.println(g.getTrump());
        System.out.println(g.getPlayers());
        System.out.println(g.getPlayersCards());

        Player source = getPlayerWhoMovedFirst(g);  // кто ходит первым
        Player target = getNextPlayingPlayer(g, source);  // кто отбивает

        Round firstRound = new Round(source, target);  // создаем 1ый раунд
        g.addRound(firstRound); // сохраняем инфрмацию о 1ом раунде
        source = GameService.playingRound(g, firstRound, true);  // играем 1 раунд и возвращаем игрока, который ходит следующим

        target = getNextPlayingPlayer(g, source);   // находим игрока, который будет отбиваться следующим
        while (isGameActive(g)) {
            Round round = new Round(source, target);   // создаем новый раунд
            g.addRound(round);  // сохраняем инфрмацию о раунде
            source = GameService.playingRound(g, round, false);
            // играем раунд и возвращаем игрока, который ходит следующим
            target = getNextPlayingPlayer(g, source);   // находим игрока, который будет отбиваться следующим
        }
    }

    private void dialCards(Game g) {   // раздача карт
        Map<Player, List<Card>> playersCards = new HashMap<>(); // лист с картами всех игорков
        for (Player p : g.getPlayers()) {
            List<Card> pc = new ArrayList<>();  // карты p-го игрока
            for (int i = 0; i < 6; i++) {
                pc.add(g.getCards().pop());
            }
            Collections.sort(pc);
            playersCards.put(p, pc);  // его карты сохранятся в лист с картами всех игроков
        }
        g.setPlayersCards(playersCards);  // сохранятся лист с картами игроков
    }

    private static Player getNextPlayingPlayer(Game g, Player playerSource) { // возвращает следующего по очереди игрока, который ещё в игре
        int sourcePlayerNumber = Integer.valueOf(playerSource.getNumber());
        List<Player> players = g.getPlayers();  // игроки
        Player nextPlayingPlayer = null;   // кто будет биться следующим
        if (sourcePlayerNumber == players.size() - 1) {  // если аттакующий под последним номером
            nextPlayingPlayer = players.get(0);   // то защищающимся должен быть нулевой
        } else if (nextPlayingPlayer.equals(playerSource)) {   // если следующим явлется он сам, значит, он остался 1, и игра окночена

        } else {
            nextPlayingPlayer = players.get(sourcePlayerNumber + 1);   // иначе защищающимся должен быть следующий
        }

        if (isPlayerActive(g, nextPlayingPlayer)) {   // если записанный следующий ещё в игре
            return nextPlayingPlayer;
        } else {
            return getNextPlayingPlayer(g, nextPlayingPlayer);  // ищем следующего после него
        }
    }

    public static Player playingRound(Game g, Round round, boolean isFirstRound) { // играем раунд
        Player source = round.getSource();
        Player target = round.getTarget();
        List<Fight> fights = new ArrayList<>();
        int maxCountFights = 0;
        if (isFirstRound) {
            maxCountFights = 5;
        } else {
            maxCountFights = 6;
        }
        int i = 0;
        while (fights.size() <= maxCountFights || fights.get(i).isCovered()) {
            Card down = GameService.attackersTurn(g, source);
            Card up = GameService.defendersMove(g, target, down);
            Fight fight = new Fight(down, up);
            fights.add(fight);
            i++;
        }
        if (fights.get(i).isCovered()) {
            round.setPickedUp(true);
        } else {
            round.setPickedUp(false);
        }
        g.addRound(round);  //сохраняем раунд
        if (round.isPickedUp()) {
            return target;
        } else {
            //передать защищающемуся все карты, которые были в этом бою
            return GameService.getNextPlayingPlayer(g, target);
        }
    }

    /*private List<Card> getThrownCards(List<Card> cards, Round round) { // все карты за 1 раунд

    }*/

    public static Card attackersTurn(Game g, Player player) { // ход атакующего (возвращает карту, которую надо будет побить)
        List<Card> cards = g.getPlayersCards().get(player); // ходит своей самой маленькой картой
        return cards.get(0);
    }

    public static Card defendersMove(Game g, Player player, Card down) { // ход защищающегося (возвращает карту, которой будем бить карту down)
        //ищем самую маленькую карту, которой можем побить карту up
        return down;
    }

    private Player getPlayerWhoMovedFirst(Game g) { // кто ходит первым
        int minRang = 14;
        int maxRang = 0;
        Card currentTrump = g.getTrump(); // текущий козырь
        Player playerWhoMovedFirst = null;

        if (currentTrump.getRank().getRank() > 10) {
            playerWhoMovedFirst = searchMinTrumpInGame(g, currentTrump);
        } else {
            playerWhoMovedFirst = searchMaxTrumpInGame(g, currentTrump);
        }
        return  playerWhoMovedFirst;
    }

    private Player searchMaxTrumpInGame(Game g, Card currentTrump) {
        Player playerWithMaxTrump = null;  // номер игрока с самым большим козырем
        Card minTrumpInGame = null;   // самый старший козырь из карт в игре
        for (int i = 0; i < g.getPlayers().size(); i++) {
            Player iPlayer = g.getPlayers().get(i);  // i-ый игрок
            List<Card> cardsIPlayer = g.getPlayersCards().get(iPlayer);  // карты i-го игроко
            Card maxTrumpInPlayer = null;   // наибольший козырь у игрока
            for (int j = cardsIPlayer.size() - 1; j > -1 ; j--) {
                if (cardsIPlayer.get(i).getSuit().equals(currentTrump)) {
                    if (maxTrumpInPlayer == null) {
                        maxTrumpInPlayer = cardsIPlayer.get(i);
                        break;
                    }
                }
            }
            for (Card card: cardsIPlayer) {
                if (card.getSuit().equals(currentTrump)) {
                    if (maxTrumpInPlayer == null) {
                        maxTrumpInPlayer = card;
                        break;
                    }
                }
            }
            if (minTrumpInGame == null && maxTrumpInPlayer != null) {
                minTrumpInGame = maxTrumpInPlayer;
                playerWithMaxTrump = iPlayer;
            } else {
                if (minTrumpInGame.compareTo(maxTrumpInPlayer) < 0) {   //если меньше 0, то заменяем
                    minTrumpInGame = maxTrumpInPlayer;
                    playerWithMaxTrump = iPlayer;
                }
            }
        }
        return playerWithMaxTrump;
    }

    private Player searchMinTrumpInGame(Game g, Card currentTrump) { // search - поиск
        Player playerWithMinTrump = null;   // игрок с самым маленьким козырем
        Card minTrumpInGame = null;   // самый младший козырь из карт в игре
        for (int i = 0; i < g.getPlayers().size(); i++) {
            Player iPlayer = g.getPlayers().get(i);   // i-ый игрок
            List<Card> cardsIPlayer = g.getPlayersCards().get(iPlayer);  // карты i-го игрока
            Card minTrumpInPlayer = null;  // наименьший козырь у игрока
            for (Card card : cardsIPlayer) {
                if (card.getSuit().equals(currentTrump)) {
                    if (minTrumpInPlayer == null) {
                        minTrumpInPlayer = card;
                        break;
                    }
                }
            }
            if (minTrumpInGame == null && minTrumpInPlayer != null) {
                minTrumpInGame = minTrumpInPlayer;
                playerWithMinTrump = iPlayer;
            } else {
                if (minTrumpInGame.compareTo(minTrumpInPlayer) > 0) { //если больше 0, то заменяем
                    minTrumpInGame = minTrumpInPlayer;
                    playerWithMinTrump = iPlayer;
                }
            }
        }
        return playerWithMinTrump;
    }

    private boolean isGameActive(Game g) { // игра еще продолжается?
        int count = 0; // кол-во игроков
        for (Player p : g.getPlayers()) {
            if (!isPlayerActive(g, p)) {
                count++;
            }
        }
        if (!isDeckEmpty(g) || count > 1) {
            return true;
        }
        return false;
    }

    private static boolean isPlayerActive(Game g, Player p) {  // игрок еще в игре?
        if (!g.getPlayersCards().get(p).isEmpty() || !isDeckEmpty(g)) {
            return true;
        }
        return false;
    }

    private static boolean isDeckEmpty(Game g) {
        if (g.getCards().isEmpty()) {
            return true;
        }
        return false;
    }
}