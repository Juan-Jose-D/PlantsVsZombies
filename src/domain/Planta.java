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

    public void morir(int fila, int columna, Tablero tablero, PlantsVsZombiesGUI gui) {
        tablero.getCelda(fila, columna).setContenido(null);
        if (this instanceof Girasol) {
            gui.restaurarCeldaGirasol(fila, columna);
        }
        gui.actualizarVista(tablero);
    }

    public abstract String getImagePath();

    public int getCosto() {
        return costo;
    }

    public int getVida(){
        return vida;
    }

    public void recibirDaño(int daño) {
        int nuevaVida = vida - daño;
        this.vida = nuevaVida;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero);

}
