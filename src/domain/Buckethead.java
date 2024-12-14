package domain;

public class Buckethead extends Zombie {
    public Buckethead(){
        super(800, 100);
    }

    public String getImagePath() {
        return "src/resources/images/buckethead.png";
    }

    public int getPuntaje(){
        return 20;
    }

    public int getCost(){
        return 20;
    }
}
