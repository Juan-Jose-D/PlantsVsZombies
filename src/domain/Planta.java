package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;

public abstract class Planta {
    protected int costo;
    protected int vida;
    protected String nombre;

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

    public String getNombre() {
        return nombre;
    }

    public abstract void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero);

}
