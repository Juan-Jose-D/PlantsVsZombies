package domain;

import presentation.PoobVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;

public abstract class Plant {
    protected int cost;
    protected int health;
    protected String name;

    public Plant(int cost, int health) {
        this.cost = cost;
        this.health = health;
    }

    public void die(int row, int column, Board board, PoobVsZombiesGUI gui) {
        board.getElement(row, column).setContent(null);
        if (this instanceof Sunflower) {
            gui.restoreElementSunflower(row, column);
        }
        gui.updateView(board);
    }

    public abstract String getImagePath();

    public abstract String getImageCardPath();

    public int getCost() {
        return cost;
    }

    public int getHealth(){
        return health;
    }


    public void receiveDamage(int damage) {
        this.health = health - damage;
    }
}
