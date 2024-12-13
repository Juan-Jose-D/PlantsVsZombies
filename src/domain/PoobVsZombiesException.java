package domain;

public class PoobVsZombiesException extends Exception{

    public static final String ERROR_OPEN = "Error al abrir el archivo";
    public static final String ERROR_SAVE = "Error al guardar el archivo";
    public static final String ERROR ="Error Interno.";

    public PoobVsZombiesException(String message) {
        super(message);
    }
}