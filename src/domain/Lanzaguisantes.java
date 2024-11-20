package domain;

public class Lanzaguisantes extends Planta{

    private final double FRECUENCIA_DISPARO = 1.5;
    private final double DAÑO = 20;

    public Lanzaguisantes() {
        super(100, 300);
    }

    public void iniciarDisparo(){
        System.out.println("Disparo");
    }
}
