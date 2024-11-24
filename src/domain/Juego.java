package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Juego {
    private PlantsVsZombiesGUI plantsVsZombiesGUI;
    private int soles;
    private Tablero tablero;
    private ScheduledExecutorService scheduler;

    public Juego(PlantsVsZombiesGUI plantsVsZombiesGUI) {
        this.tablero = new Tablero(5, 8);
        this.plantsVsZombiesGUI = plantsVsZombiesGUI;
        this.soles = 50;
        this.scheduler = Executors.newScheduledThreadPool(10);
    }

    public void iniciarJuego(String dificultad) {
        configurarZombiesPorDificultad(dificultad);
    }

    public void iniciarAccionPlanta(Planta planta) {
        planta.iniciarAcciones(scheduler, plantsVsZombiesGUI, tablero);
    }

    public boolean colocarPlanta(Planta planta, int fila, int columna) {
        if (planta.getCosto() <= soles && tablero.isEmpty(fila,columna)) {
            tablero.agregarPlanta(planta,fila,columna);
            restarSoles(planta.getCosto());
            return true;
        }
        return false;
    }

    private void configurarZombiesPorDificultad(String dificultad) {
        switch (dificultad) {
            case "Fácil":
                // Configuración para nivel fácil
                break;
            case "Medio":
                // Configuración para nivel medio
                break;
            case "Difícil":
                // Configuración para nivel difícil
                break;
        }
    }

    public void iniciarAparicionDeZombis() {
        scheduler.scheduleAtFixedRate(() -> {
            Zombi nuevoZombi = new Zombi();
            actualizarEstado();
        }, 0, 10, TimeUnit.SECONDS);
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
