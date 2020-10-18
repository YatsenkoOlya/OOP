package com.company;

public class Player {
    private String number;

    public Player(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Player");
        sb.append(number);
        return sb.toString();
    }
}
