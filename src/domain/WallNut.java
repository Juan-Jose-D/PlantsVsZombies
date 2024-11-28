package domain;

import presentation.PoobVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WallNut extends Plant {

    private PoobVsZombies game;

    public WallNut(PoobVsZombies game) {
        super(50, 4000);
        this.game = game;

    }

    public void startActions(ScheduledExecutorService scheduler, PoobVsZombiesGUI gui, Board board) {
        scheduler.scheduleAtFixedRate(() -> {
        }, 0, 20, TimeUnit.SECONDS);
    }

    public String getImagePath() {
        return "src/resources/images/nuez.png";
    }
}
