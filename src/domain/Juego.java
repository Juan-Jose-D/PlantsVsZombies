package domain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Juego {
    private int soles;
    private Tablero tablero;
    private ScheduledExecutorService scheduler;

    public Juego() {
        this.soles = 50;
        this.scheduler = Executors.newScheduledThreadPool(10); // Manejar varias tareas concurrentes
    }

    public void iniciarJuego() {
        System.out.println("Juego iniciado.");
        // Ejemplo de programación específica para un girasol
        scheduler.scheduleAtFixedRate(() -> {
            // Suponiendo que tienes una referencia al girasol
            //tablero.getCelda(0, 0).getContenido().realizarAccion();
        }, 0, 20, TimeUnit.SECONDS);

        // Ejemplo de programación específica para un lanzaguisantes
        scheduler.scheduleAtFixedRate(() -> {
           // tablero.getCelda(0, 1).getContenido().realizarAccion();
        }, 0, 1500, TimeUnit.MILLISECONDS);

        // Actualización global del juego
        scheduler.scheduleAtFixedRate(this::actualizarEstado, 0, 1, TimeUnit.SECONDS);
    }

    public boolean colocarPlanta(Planta planta, int fila, int columna) {
        if (planta.getCosto() <= soles) {
            tablero.getCelda(fila, columna).setContenido(planta);
            restarSoles(planta.getCosto());
            return true;
        }
        return false;
    }

    public void actualizarEstado() {
        System.out.println("Estado del juego actualizado.");
        // Aquí podrías manejar colisiones, mover zombis, etc.
    }

    public void finish() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    public int getSoles() {
        return soles;
    }

    public void agregarSoles(int cantidad) {
        soles += cantidad;
        System.out.println("Soles: " + soles);
    }

    public void restarSoles(int cantidad) {
        soles = Math.max(0, soles - cantidad);
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
}
