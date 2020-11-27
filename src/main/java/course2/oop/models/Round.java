package course2.oop.models;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private Player source;
    private Player target;
    private List<Fight> fights = new ArrayList();
    private boolean isPickedUp = false;  // бито или стянул
    private List<Rank> allRanksInRound = new ArrayList<>();

    public Round(Player source, Player target) {
        this.source = source;
        this.target = target;
    }

    public Player getSource() {
        return source;
    }

    public void setSource(Player source) {
        this.source = source;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public List<Fight> getFights() {
        return fights;
    }

    // добавляем все бои за 1 раунд
    public void setFights(List<Fight> fights) {
        this.fights = fights;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        isPickedUp = pickedUp;
    }

    public List<Rank> getAllRanksInRound() {
        return allRanksInRound;
    }

    public void setAllRanksInRound(List<Rank> allRanksInRound) {
        this.allRanksInRound = allRanksInRound;
    }

    public void addRank(Rank rank) {    //добавляем номинал карты
        if (!this.nominalInRound(rank)) {
            allRanksInRound.add(rank);      //если нет, то добавляем
        }
    }

    public boolean nominalInRound(Rank rank) {  //проверяем наличие номинала rank в раунде
        for (Rank r: allRanksInRound) {         //цикл по всем сохранённым номиналам
            if (r.equals(rank)) {               //если rank совпадает с r, то такой номинал есть
                return true;                    //иначе такого номинала нет
            }
        }
        return false;
    }
}
