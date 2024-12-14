package domain;

public class Basic extends Zombie {


    public Basic(){
        super(100, 100);
    }

    public String getImagePath() {
        return "src/resources/images/basic.png";
    }

    public int getPuntaje(){
        return 10;
    }

    public int getCost(){
        return 20;
    }
}
