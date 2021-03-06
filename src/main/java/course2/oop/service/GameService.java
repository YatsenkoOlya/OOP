package course2.oop.service;
import course2.oop.models.*;

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
        g.setTrumpCard(trump);
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
        System.out.println((char) 27 + "[43m" + g.getTrumpCard() + (char) 27 + "[0m");
        System.out.println(playerCardsToString(g));
        System.out.println(g.getCards().size());

        Player source = getPlayerWhoMovedFirst(g);  // кто ходит первым
        System.out.print("---1 раунд---\nиграют " + source.toString());
        Player target = getNextPlayingPlayer(g, source);  // кто отбивает
        System.out.println(" и " + target.toString());

        Round firstRound = new Round(source, target);  // создаем 1ый раунд
        source = playingRound(g, firstRound, true);  // играем 1 раунд и возвращаем игрока, который ходит следующим
        g.addRound(firstRound); // сохраняем инфрмацию о 1ом раунде
        target = getNextPlayingPlayer(g, source);   // находим игрока, который будет отбиваться следующим
        int i = 2;
        while (isGameActive(g)) {
            System.out.println("---| " + i + " раунд |---\nиграют " + source.toString() + " и " + target.toString());
            Round round = new Round(source, target);   // создаем новый раунд
            source = playingRound(g, round, false);
            g.addRound(round);  // сохраняем инфрмацию о раунде
            // играем раунд и возвращаем игрока, который ходит следующим
            if (source == null) {
                break;
            }
            target = getNextPlayingPlayer(g, source);   // находим игрока, который будет отбиваться следующим
            if (target == null) {
                break;
            }
            i++;
        }
        Player stupid = getLastPlayer(g);
        if (stupid == null) {
            System.out.println("ничья");
        } else {
            System.out.println("----| " + stupid + " проиграл |----");
        }
        System.out.println((char) 27 + "[31m|||---||| Конец игры |||---|||" + (char) 27 + "[0m");
    }

    private void dialCards(Game g) {   // раздача карт
        Map<Player, List<Card>> playersCards = new HashMap<>(); // лист с картами всех игорков

        for (Player p : g.getPlayers()) {
            List<Card> pc = new ArrayList<>();  // карты p-го игрока
            for (int i = 0; i < 6; i++) {
                pc.add(g.getCards().pop());
            }
            Collections.sort(pc);

            pc = addTrumpsToEnd(pc, g.getTrumpCard().getSuit());

            playersCards.put(p, pc);  // его карты сохранятся в лист с картами всех игроков
        }
        g.setPlayersCards(playersCards);  // сохраняется лист с картами игроков
    }

    private List<Card> addTrumpsToEnd(List<Card> cards, Suit trump) { //перестевляем козыри в конец
        List<Card> newListCards = List.copyOf(cards);
        Card lastCard = newListCards.get(newListCards.size() - 1);
        int i = 0;
        while (newListCards.get(i) != lastCard) {
            if (newListCards.get(i).getSuit() == trump) {
                newListCards = movingCardToEnd(newListCards.get(i), newListCards, i);
            } else {
                i++;
            }
        }
        if (newListCards.get(i).getSuit() == trump) {
            newListCards = movingCardToEnd(newListCards.get(i), newListCards, i);
        }
        return newListCards;
    }

    private List<Card> movingCardToEnd(Card card, List<Card> cards, int number) {
        List<Card> newCards = new ArrayList<>();
        addFirstCards(newCards, cards, number);
        addLastCards(newCards, cards, number + 1);
        newCards.add(card);
        return newCards;
    }

    private List<Card> addFirstCards(List<Card> newCards, List<Card> cards, int i) { // добавление в newCards всех элементов из cards
                                                                                     // до элемента под номером i
        for (int j = 0; j < i; j++) {
            newCards.add(cards.get(j));
        }
        return newCards;
    }

    private List<Card> addLastCards(List<Card> newCards, List<Card> cards, int i) { // добавление в newCards всех элементов из cards,
                                                                                    // начиная с элемента под номером i
        for (int j = i; j < cards.size(); j++) {
            newCards.add(cards.get(j));
        }
        return newCards;
    }

    private Player getPlayerWhoMovedFirst(Game g) { // кто ходит первым
        int minRang = 14;
        int maxRang = 0;
        Card currentTrump = g.getTrumpCard(); // текущий козырь
        Player playerWhoMovedFirst = null;

        if (currentTrump.getRank().getRank() > 10) {
            playerWhoMovedFirst = searchMinTrumpInGame(g, g.getTrumpCard().getSuit());
            System.out.println("Самый младший козырь у " + playerWhoMovedFirst.toString() + ", поэтому он ходит первый");
        } else {
            playerWhoMovedFirst = searchMaxTrumpInGame(g, g.getTrumpCard().getSuit());
            System.out.println("Самый старший козырь у " + playerWhoMovedFirst.toString() + ", поэтому он ходит первый");
        }
        if (playerWhoMovedFirst == null) {
            playerWhoMovedFirst = searchMaxCardInGame(g);
            System.out.println("У игроков на руках нет козырей, самая старшая карта у " + playerWhoMovedFirst);
        }
        return playerWhoMovedFirst;
    }

    private Player searchMaxTrumpInGame(Game g, Suit currentTrump) {
        Player playerWithMaxTrump = null;  // номер игрока с самым большим козырем
        Card maxTrumpInGame = null;   // самый старший козырь из карт в игре
        for (int i = 0; i < g.getPlayers().size(); i++) {
            Player iPlayer = g.getPlayers().get(i);  // i-ый игрок
            List<Card> cardsIPlayer = g.getPlayersCards().get(iPlayer);  // карты i-го игроко
            Card maxTrumpInPlayer = null;   // наибольший козырь у игрока
            for (int j = cardsIPlayer.size() - 1; j > -1 ; j--) {
                if (cardsIPlayer.get(i).getSuit() == currentTrump) {
                    if (maxTrumpInPlayer == null) {
                        maxTrumpInPlayer = cardsIPlayer.get(i);
                        break;
                    }
                }
            }
            for (Card card: cardsIPlayer) {
                if (card.getSuit() == currentTrump) {
                    if (maxTrumpInPlayer == null) {
                        maxTrumpInPlayer = card;
                        break;
                    }
                }
            }
            if (maxTrumpInPlayer != null) {
                if (maxTrumpInGame == null) {
                    maxTrumpInGame = maxTrumpInPlayer;
                    playerWithMaxTrump = iPlayer;
                } else {
                    if (maxTrumpInGame.compareTo(maxTrumpInPlayer) < 0) {   //если меньше 0, то заменяем
                        maxTrumpInGame = maxTrumpInPlayer;
                        playerWithMaxTrump = iPlayer;
                    }
                }
            }
        }

        //если ни у кого нет козыря
        return playerWithMaxTrump;
    }

    private Player searchMinTrumpInGame(Game g, Suit currentTrump) { // search - поиск
        Player playerWithMinTrump = null;   // игрок с самым маленьким козырем
        Card minTrumpInGame = null;   // самый младший козырь из карт в игре
        for (int i = 0; i < g.getPlayers().size(); i++) {
            Player iPlayer = g.getPlayers().get(i);   // i-ый игрок
            List<Card> cardsIPlayer = g.getPlayersCards().get(iPlayer);  // карты i-го игрока
            Card minTrumpInPlayer = null;  // наименьший козырь у игрока
            for (Card card : cardsIPlayer) {
                if (card.getSuit() == currentTrump) {
                    if (minTrumpInPlayer == null) {
                        minTrumpInPlayer = card;
                        break;
                    }
                }
            }
            if (minTrumpInPlayer != null) {
                if (minTrumpInGame == null) {
                    minTrumpInGame = minTrumpInPlayer;
                    playerWithMinTrump = iPlayer;
                } else {
                    if (minTrumpInGame.compareTo(minTrumpInPlayer) > 0) { //если больше 0, то заменяем
                        minTrumpInGame = minTrumpInPlayer;
                        playerWithMinTrump = iPlayer;
                    }
                }
            }
        }
        return playerWithMinTrump;
    }

    private Player searchMaxCardInGame(Game g) {
        List<Player> players = g.getPlayers();
        Map<Player, List<Card>> playersCards = g.getPlayersCards();
        Player playerWithMaxCard = players.get(0);

        List<Card> cards = playersCards.get(playerWithMaxCard);
        Card maxCard = cards.get(cards.size() - 1);
        for (int i = 1; i < players.size(); i++) {
            cards = playersCards.get(players.get(i));
            Card maxPlayerCard = cards.get(cards.size() - 1);
            if (maxCard.compareTo(maxPlayerCard) < 0) {
                maxCard = maxPlayerCard;
                playerWithMaxCard = players.get(i);
            }
        }
        return playerWithMaxCard;
    }

    private Player getNextPlayingPlayer(Game g, Player playerSource) { // возвращает следующего по очереди игрока, который ещё в игре
        int playerSourceNumber = Integer.valueOf(playerSource.getNumber());
        List<Player> players = g.getPlayers();
        Player nextPlayer;
        if (playerSourceNumber == players.size()) {
            nextPlayer = searchNextPlayer(g, players, 0, players.size() - 1);
        } else if (playerSourceNumber == 1){
            nextPlayer = searchNextPlayer(g, players, 1, players.size());
        } else {
            nextPlayer = searchNextPlayer(g, players, playerSourceNumber, players.size());
            if (nextPlayer == null) {
                nextPlayer = searchNextPlayer(g, players, 0, playerSourceNumber - 1);
            }
        }
        return nextPlayer;
    }

    private Player searchNextPlayer(Game g, List<Player> players, int start, int finish) {
        Player nextPlayer = null;
        for (int i = start; i < finish; i++) {
            if (isPlayerActive(g, players.get(i))) {
                nextPlayer = players.get(i);
                break;
            }
        }
        return nextPlayer;
    }

    public Player playingRound(Game g, Round round, boolean isFirstRound) { // играем раунд
        Player source = round.getSource();
        Player target = round.getTarget();
        List<Fight> fights = new ArrayList<>();
        Player nextPlayer = null;
        int maxCountFights = isFirstRound ? 5: 6;

        int i = 0;
        Card down = null;
        Card up = null;

        while (fights.size() <= maxCountFights) {
            down = attackersTurn(g, round, source, i);
            if (down != null) { //если игрок сходил(не сказал бито)
                System.out.println("-*-*-| " + (i + 1) + " бой |-*-*-");
                up = defendersMove(g, target, round, down); //тогда необходимо биться
                System.out.println(down.toString());
            } else {
                System.out.println("-*-*-*-*-*-*-*-*-*-\n" + target.toString() + " отбился");
                break;
            }

            Fight fight = new Fight(down, up);
            fights.add(fight);
            round.setFights(fights);

            if (!fight.isCovered()) {
                System.out.println(target.toString() + " берёт");
                round.setPickedUp(false);
                List<Card> cardsInRound = getCardsInRound(round);
                for (Card card: cardsInRound) {
                    addCard(g, target, card);
                }
                if (isGameActive(g)) {
                    nextPlayer = getNextPlayingPlayer(g, target);
                } else {
                    System.out.println(playerCardsToString(g));
                    return null;
                    //игрок target проиграл
                }
                break;
            } else {
                System.out.println(up.toString());
                round.setPickedUp(true);
                if (isPlayerActive(g, target)) {
                    nextPlayer = target;
                } else {
                    nextPlayer = getNextPlayingPlayer(g, target);
                }
            }
            i++;
        }

         //игрок добирает карты до 6 (если ему это необходимо)
        if (g.getPlayersCards().get(source).size() < 6) {
            addCardToFull(g, source);
        }
        if (g.getPlayersCards().get(target).size() < 6) {
            addCardToFull(g, target);
        }

        g.addRound(round);  //сохраняем раунд

        System.out.println(playerCardsToString(g));
        System.out.println(g.getCards().size());
        return nextPlayer;
    }

    private List<Card> getCardsInRound(Round round) { // все карты за раунд
        List<Card> cardsInRound = new ArrayList<>();
        for(Fight fight: round.getFights()) {
            cardsInRound.add(fight.getDown());
            if (fight.isCovered()) {
                cardsInRound.add(fight.getUp());
            }
        }
        return cardsInRound;
    }

    private void addCardToFull(Game g, Player player) { // добор карт игроком после раунда
        List<Card> cards = g.getPlayersCards().get(player);
        Stack<Card> deck = g.getCards();
        int n = 6 - cards.size(); // сколько карт не хватает
        for (int i = 0; i < n; i++) {
            if (deck.size() != 0) {
                cards = addCard(g, cards, deck.pop());
            } else {
                break;
            }
        }
        Map<Player, List<Card>> playerCards = g.getPlayersCards();
        playerCards.put(player, cards);
        g.setPlayersCards(playerCards);
    }

    public void addCard(Game g, Player player, Card card) {  // добавление карты игроку
        List<Card> cards = g.getPlayersCards().get(player);
        List<Card> newCards = addCard(g, cards, card);
        g.getPlayersCards().put(player, newCards);
    }

    public List<Card> addCard(Game g, List<Card> cards, Card card) {
        List<Card> newCards = new ArrayList<>();
        Suit trump = g.getTrumpCard().getSuit();

        int i = 0;
        if (card.getSuit() != trump) {
            while (i < cards.size() && cards.get(i).getSuit() != trump) {
                if (card.compareTo(cards.get(i)) <= 0) {
                    break;
                }
                i++;
            }
        } else {
            while (i < cards.size() && cards.get(i).getSuit() != trump) {
                i++;
            }
            while (i < cards.size()) {
                if (card.compareTo(cards.get(i)) <= 0) {
                    break;
                }
                i++;
            }
        }
        addFirstCards(newCards, cards, i);
        newCards.add(card);
        addLastCards(newCards, cards, i);
        return newCards;
    }

    public Card attackersTurn(Game g, Round round, Player player, int i) { // ход атакующего (возвращает карту, которую надо будет побить)
        List<Card> cards = g.getPlayersCards().get(player); // ходит своей самой маленькой картой
        Card card = null; //карта, которой он сходил
        int numberCard = 0;
        if (i == 0) {
            card = cards.get(i);
            numberCard = i;
        } else {
            //сходить самой маленькой подходящей картой игрока, которая при этом не козырная
            for (int j = 0; j < cards.size(); j++) {
                if (round.nominalInRound(cards.get(j).getRank())) {
                    card = cards.get(j);
                    numberCard = j;
                    break;
                }
            }
        }

        if (card == null) { //если card == null, то значит игрок не может сходить и автоматически бито
            round.setPickedUp(true);
        } else {
            List<Card> newCards = new ArrayList<>();
            addFirstCards(newCards, cards, numberCard);
            addLastCards(newCards, cards, numberCard + 1);

            g.getPlayersCards().put(player, newCards);
            round.addRank(card.getRank());
        }
        return card;
    }

    public Card defendersMove(Game g, Player player, Round round, Card down) {
        //ход защищающегося (возвращает карту, которой будем бить карту down)
        //ищем самую маленькую карту, которой можем побить карту down
        List<Card> cards = g.getPlayersCards().get(player);
        Card cardForMove = null;
        int numberCardForMove = 0;
        boolean isTrump = (down.getSuit() == g.getTrumpCard().getSuit());  //карта, которую нужно побить
                                                                          //является козырем
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).compareTo(down) > 0) { //>0, значит карта card больше по номиналу чем карта down
                if (cards.get(i).getSuit() == down.getSuit()) {
                    cardForMove = cards.get(i);
                    numberCardForMove = i;
                    break;
                }
            }

            //может побить и козырной картой
            if (!isTrump) {                                     //если карта, которую нужно побить не является козырем
                if (cards.get(i).getSuit() == g.getTrumpCard().getSuit()) { //то мы можем побить её любой козырной картой
                    cardForMove = cards.get(i);                         //(самой маленькой)
                    numberCardForMove = i;
                    break;
                }
            }
        }

        if (cardForMove != null) {
            round.addRank(cardForMove.getRank());
            List<Card> newCards = new ArrayList<>();
            addFirstCards(newCards, cards, numberCardForMove);
            addLastCards(newCards, cards, numberCardForMove + 1);
            g.getPlayersCards().put(player, newCards);
        }
        return cardForMove;
    }

    private String playerCardsToString(Game g) {
        String playerCards = "";
        Suit trump = g.getTrumpCard().getSuit();
        for (int i = 0; i < g.getPlayers().size(); i++) {
            Player pl = g.getPlayers().get(i);
            playerCards += pl + " = [";
            List<Card> cards = g.getPlayersCards().get(pl);
            if (cards.size() == 0) {
                playerCards += "none";
            } else {
                for (int j = 0; j < cards.size() - 1; j++) {
                    if (cards.get(j).getSuit() == trump) {
                        playerCards += (char) 27 + "[43m" + cards.get(j) + (char) 27 + "[0m ";
                    } else {
                        playerCards += cards.get(j) + " ";
                    }
                }

                Card lastCard = cards.get(cards.size() - 1);
                if (lastCard.getSuit() == trump) {
                    playerCards += (char) 27 + "[43m" + lastCard + (char) 27 + "[0m";
                } else {
                    playerCards += lastCard;
                }
            }
            playerCards += "]";
            if (i != g.getPlayers().size() - 1) {
                playerCards += ", ";
            }
        }
        return playerCards;
    }

    private boolean isGameActive(Game g) { // игра еще продолжается?
        int count = 0; // кол-во игроков
        for (Player p : g.getPlayers()) {
            if (!isPlayerActive(g, p)) {
                count++;
            }
        }
        if (count <= 1) {
            return true;
        }
        return false;
    }

    private boolean isPlayerActive(Game g, Player p) {  // игрок еще в игре?
        if (!g.getPlayersCards().get(p).isEmpty() || !isDeckEmpty(g)) {
            return true;
        }
        return false;
    }

    private boolean isDeckEmpty(Game g) {
        if (g.getCards().isEmpty()) {
            return true;
        }
        return false;
    }

    private Player getLastPlayer(Game g) {
        Player lastPlayer = null;
        for (int i = 0; i < g.getPlayers().size(); i++) {
            lastPlayer = g.getPlayers().get(i);
            if (isPlayerActive(g, lastPlayer)) {
                break;
            }
        }
        return lastPlayer;
    }
}