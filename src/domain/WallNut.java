package domain;

import presentation.PoobVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WallNut extends Plant {

    public WallNut(PoobVsZombies game) {
        super(50, 4000);

    }

    public String getImagePath() {
        return "src/resources/images/walnut.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/wallnutCard.png";
    }
}
