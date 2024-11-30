package domain;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import presentation.PoobVsZombiesGUI;

public class Sunflower extends Plant {
    private static final int GENERATION_RATE = 20;
    private static final int SUNS_AMOUNT = 25;

    private final PoobVsZombies game;
    private boolean availableSun;
    private int row;
    private int column;

    public Sunflower(PoobVsZombies game) {
        super(50, 300);
        this.game = game;
        this.availableSun = false;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean sunAvailable() {
        return availableSun;
    }

    public void gatherSun() {
        if (availableSun) {
            game.addSuns(SUNS_AMOUNT);
            availableSun = false;
        }
    }

    public void startActions(ScheduledExecutorService scheduler, PoobVsZombiesGUI gui, Board board) {
        scheduler.scheduleAtFixedRate(() -> {
            if (!availableSun) {
                availableSun = true;
                gui.iluminateElementSunflower(row, column);
            }
        }, GENERATION_RATE, GENERATION_RATE, TimeUnit.SECONDS);
    }
    

    public String getImagePath() {
        return "src/resources/images/sunflower.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/sunflowerCard.png";
    }
}
