package domain;

import presentation.PoobVsZombiesGUI;

public class Peashooter extends Plant{
    int RATE_OF_FIRE = 1500;

    public Peashooter(PoobVsZombies game) {
        super(100, 300);
    }


    public void attack(int row, PoobVsZombiesGUI gui, Board board) {
        for (int column = 0; column < board.getColumns(); column++){
            if (board.getElement(row, column).getContent() instanceof Zombie zombie){
                int DAMAGE = 25;
                zombie.receiveDamage(DAMAGE);
                if (zombie.getHealth() <= 0){
                    zombie.die(row, column, board, gui);
                }
            }
        }
    }

    public String getImagePath() {
        return "src/resources/images/peashooter.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/peashooterCard.png";
    }

    public int getRATE_OF_FIRE(){
        return RATE_OF_FIRE;
    }
}
