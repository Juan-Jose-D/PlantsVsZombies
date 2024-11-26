package domain;

import java.io.File;
import java.net.URL;

public class Cubeta extends Zombi{

    public Cubeta(){
        super(800, 100);
    }

    public String getImagePath() {
        return "src/resources/images/buckethead.png";
    }
}
