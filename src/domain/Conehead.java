package domain;

public class Conehead extends Zombie {

    public Conehead(){
        super(380, 100);
    }

    public String getImagePath() {
        return "src/resources/images/conehead.png";
    }

    public int getPuntaje(){
        return 15;
    }

    public int getCost(){
        return 20;
    }
}
