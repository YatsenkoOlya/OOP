package com.company;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private Player source; // кто ходит
    private Player target; // кто отбивается
    private List<Fight> fights = new ArrayList(); // борьба
    private boolean isPickedUp = false;  // бито или стянул

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
}
