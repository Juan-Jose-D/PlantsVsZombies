package domain;

import presentation.PoobVsZombiesGUI;

import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PotatoMine extends Plant {
    private static final int COOLDOWN = 20;

    private final PoobVsZombies game;

    public PotatoMine(PoobVsZombies game) {
        super(25, 100);
        this.game = game;
    }

    public void attack(int row, int column, PoobVsZombiesGUI gui, Board board) {
        Queue<Zombie> zombieQueue = game.getZombieQueueForRow(row);

        if (!zombieQueue.isEmpty()) {
            Zombie zombie = zombieQueue.peek();

            int zombieRow = board.getRowObject(zombie);
            int zombieColumn = board.getColumnObject(zombie);
            if ((zombieColumn - column) == 1){
                zombie.die(zombieRow, zombieColumn , board, gui);
                game.addPuntaje(zombie.getPuntaje());
                zombieQueue.poll();
                die(row, column, board, gui);
            }
        }
    }

    public int getCooldown(){
        return COOLDOWN;
    }
    public String getImagePath() {
        return "src/resources/images/potato-mine.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/MineCard.png";
    }
}
