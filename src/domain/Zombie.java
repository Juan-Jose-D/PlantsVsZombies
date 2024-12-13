package domain;

import presentation.PoobVsZombiesGUI;

public abstract class Zombie  {

    protected int health;
    protected int damage;

    public Zombie(int health, int damage){
        this.health = health;
        this.damage = damage;
    }

    public abstract String getImagePath();

    public void receiveDamage(int damage){
        this.health = health - damage;
    }

    public int getDamage(){
        return damage;
    }

    public int getHealth(){
        return health;
    }

    public void die(int row, int column, Board board, PoobVsZombiesGUI gui) {
        board.getElement(row, column).setContent(null);
        gui.updateView(board);
    }
}
