package domain;

import java.io.File;
import java.net.URL;

public class Basico extends Zombi{


    public Basico(){
        super(100, 100);
    }

    public String getImagePath() {
        return "src/resources/images/basic.png";
    }
}
