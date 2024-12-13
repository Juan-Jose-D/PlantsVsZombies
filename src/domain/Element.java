package domain;

import java.io.Serializable;

public class Element implements Serializable {
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
