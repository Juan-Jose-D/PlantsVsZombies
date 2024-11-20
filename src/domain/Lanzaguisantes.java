package domain;

public class Lanzaguisantes extends Planta{

    private final double FRECUENCIA_DISPARO = 1.5;

    public Lanzaguisantes() {
        super(100, 300);
    }

    public void iniciarDisparo(){
        System.out.println("Disparo");
    }
}
