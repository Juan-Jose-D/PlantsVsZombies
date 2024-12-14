package domain;

import presentation.PoobVsZombiesGUI;

import java.util.Queue;

public class Peashooter extends Plant{
    int RATE_OF_FIRE = 1500;
    private PoobVsZombies game;

    public Peashooter(PoobVsZombies game) {
        super(100, 300);
        this.game = game;
    }


    public void attack(int row, PoobVsZombiesGUI gui, Board board) {
        Queue<Zombie> zombieQueue = game.getZombieQueueForRow(row);

        if (!zombieQueue.isEmpty()) {
            Zombie zombie = zombieQueue.peek();

            int DAMAGE = 25;
            int zombieRow = board.getRowObject(zombie);
            int zombieColumn = board.getColumnObject(zombie);
            zombie.receiveDamage(DAMAGE);

            if (zombie.getHealth() <= 0) {
                zombie.die(zombieRow, zombieColumn , board, gui);
                game.addPuntaje(zombie.getPuntaje());
                zombieQueue.poll();
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
