package domain;

import presentation.PoobVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PotatoMine extends Plant {
    private static final int COOLDOWN = 20;

    private final PoobVsZombies game;

    public PotatoMine(PoobVsZombies game) {
        super(25, 100);
        this.game = game;
    }


    public void startActions(ScheduledExecutorService scheduler, PoobVsZombiesGUI gui, Board board) {

    }

    public String getImagePath() {
        return "src/resources/images/potato-mine.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/MineCard.png";
    }
}
