package course2.oop.models;

public class Fight { // борьба

    public Fight(Card down, Card up) {
        this.down = down;
        this.up = up;
    }

    private Card down;
    private Card up;

    public Card getDown() {
        return down;
    }

    public void setDown(Card down) {
        this.down = down;
    }

    public Card getUp() {
        return up;
    }

    public void setUp(Card up) {
        this.up = up;
    }

    public boolean isCovered() { // если бито
        return up != null;
    }
}
