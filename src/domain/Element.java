package domain;

public class Element {
    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        if (content instanceof Plant || content instanceof Zombie || content instanceof LawnMower) {
            this.content = content;
        } else {
            throw new IllegalArgumentException("Solo se permiten objetos de tipo Plant, Zombie o LawnMower.");
        }
    }
}
