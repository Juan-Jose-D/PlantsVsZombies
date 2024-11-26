package domain;

import java.io.File;
import java.net.URL;

public class Cono extends Zombi{

    public Cono(){
        super(380, 100);
    }

    public String getImagePath() {
        return "src/resources/images/conehead.png";
    }
}
