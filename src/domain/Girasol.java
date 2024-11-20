package domain;

public class Girasol extends Planta{
    private static final int FRECUENCIA_GENERACION = 20;

    private Juego juego;

    public Girasol(){
        super(50, 300);
    }

    public void generarSol(){
        juego.agregarSoles(25);
    }

}
