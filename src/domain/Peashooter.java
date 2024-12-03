package domain;

import presentation.PoobVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Peashooter extends Plant{

    private PoobVsZombies game;

    public Peashooter(PoobVsZombies game) {
        super(100, 300);
        this.game = game;
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

    public void startActions(ScheduledExecutorService scheduler, PoobVsZombiesGUI gui, Board board){
    }

    public void startActions(ScheduledExecutorService scheduler, PoobVsZombiesGUI gui, Board board, int row){
        int RATE_OF_FIRE = 1500;
        scheduler.scheduleAtFixedRate(() -> {
            attack(row, gui, board);
        }, RATE_OF_FIRE, RATE_OF_FIRE, TimeUnit.MILLISECONDS);
    }

    public String getImagePath() {
        return "src/resources/images/peashooter.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/peashooterCard.png";
    }
}
