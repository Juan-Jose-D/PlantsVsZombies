package domain;

public class PoobVsZombiesException extends Exception{

    public static final String SAVE_EXCEPTION = "Error al guardar el juego.";
    public static final String LOAD_EXCEPTION = "Error al cargar el juego.";

    public PoobVsZombiesException(String message) {
        super(message);
    }
}