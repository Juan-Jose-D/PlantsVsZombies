package domain;

public class Brainstein extends Zombie{
    public Brainstein() {
        super(300, 0);
    }

    public String getImagePath() {
        return "src/resources/images/brainstein.png";
    }

    public int getPuntaje() {
        return 20;
    }

    public int getCost() {
        return 50;
    }
}

