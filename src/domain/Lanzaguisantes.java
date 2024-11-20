package domain;

public class Lanzaguisantes extends Planta{

    private final double FRECUENCIA_DISPARO = 1.5;
    private static final int VIDA = 300;
    private static final int COSTO = 100;

    public Lanzaguisantes() {

    }

    public void iniciarDisparo(){
        System.out.println("Disparo");
    }

    public int getCosto(){
        return COSTO;
    }
}
