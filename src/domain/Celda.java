package domain;

public class Celda {
    private Object contenido; // Puede ser una Planta, un Zombi o null

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
}
