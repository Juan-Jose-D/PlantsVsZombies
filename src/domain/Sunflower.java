package domain;

public class Sunflower extends Plant {

    private static final int SUNS_AMOUNT = 25;

    private final PoobVsZombies game;
    private boolean availableSun;
    private boolean canGenerateSun;

    public Sunflower(PoobVsZombies game) {
        super(50, 300);
        this.game = game;
        this.availableSun = false;
        this.canGenerateSun = true;
    }

    public boolean sunAvailable() {
        return availableSun;
    }

    public void generateSun() {
        if (canGenerateSun && !availableSun) {
            availableSun = true;
            canGenerateSun = false;
        }
    }

    public void gatherSun() {
        if (availableSun) {
            game.addSuns(SUNS_AMOUNT);
            availableSun = false;
            canGenerateSun = true;
        }
    }

    public String getImagePath() {
        return availableSun
                ? "src/resources/images/sunflower_sun.png"
                : "src/resources/images/sunflower.gif";
    }

    public String getImageCardPath() {
        return "src/resources/images/sunflowerCard.png";
    }

}
