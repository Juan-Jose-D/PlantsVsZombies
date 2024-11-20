package domain;

public class Planta {
    protected int costo;
    protected int vida;

    public Planta(int costo, int vida) {
        this.costo = costo;
        this.vida = vida;
    }

    public void morir() {
        System.out.println("Planta murió");
    }

    public int getCosto() {
        return costo;
    }
}
