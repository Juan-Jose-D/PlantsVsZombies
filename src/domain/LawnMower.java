package domain;

public class LawnMower {
    private int row;
    private boolean activated;

    public LawnMower(int row) {
        this.row = row;
        this.activated = false;
    }

    public int getRow() {
        return row;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        this.activated = true;
    }

    public String getImagePath() {
        return "src/resources/images/lawnMower.png";
    }
}
