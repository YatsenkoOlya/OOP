package com.company;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private Player source;
    private Player target;

    private List<Fight> fights = new ArrayList();

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

    public void setFights(List<Fight> fights) {
        this.fights = fights;
    }
}
