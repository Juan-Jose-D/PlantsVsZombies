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

        // Si la planta es un girasol, asegurarnos de restaurar el color de la celda
        if (this instanceof Girasol) {
            gui.restaurarCeldaGirasol(fila, columna);
        }

        // Actualizar la vista del tablero
        gui.actualizarVista(tablero);
    }

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
