package domain;

public class Girasol extends Planta{
    private static final int FRECUENCIA_GENERACION = 20;
    private static final int VIDA = 300;
    private static final int COSTO = 50;
    private Juego juego;

    public Girasol(){

    }

    public void generarSol(){
        juego.agregarSoles(25);
    }

    public int getCosto(){
        return COSTO;
    }
}
