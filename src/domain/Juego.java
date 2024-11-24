package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

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

    public void iniciarJuego(String modo) {
        configurarSegunModo(modo);
    }

    public void configurarSegunModo(String modo){
        switch (modo){
            case "PvM":
                playerVsMachine();
                break;

            case "MvM":
                break;

            case "PvP":
                break;
            default:
        }
    }

    public void playerVsMachine() {
        scheduler.scheduleAtFixedRate(() -> {
            Random random = new Random();
            boolean zombiAgregado = false;

            for (int intentos = 0; intentos < 10 && !zombiAgregado; intentos++) {
                int filaAleatoria = random.nextInt(tablero.getFilas());
                int columna = tablero.getColumnas() - 1;
                if (tablero.isEmpty(filaAleatoria, columna)) {
                    Zombi zombi = chooseZombi();
                    tablero.agregarZombi(zombi, filaAleatoria, columna);
                    plantsVsZombiesGUI.actualizarCeldaZombi(filaAleatoria, columna, zombi);
                    zombiAgregado = true;
                }
            }
            actualizarEstado();
        }, 0, 10, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::actualizarEstado, 0, 1, TimeUnit.SECONDS);
    }

    public Zombi chooseZombi(){
        Random random = new Random();
        int tipoZombi = random.nextInt(3);

        Zombi zombiSeleccionado;

        switch (tipoZombi) {
            case 0:
                zombiSeleccionado = new Basico();
                break;
            case 1:
                zombiSeleccionado = new Cono();
                break;
            case 2:
                zombiSeleccionado = new Cubeta();
                break;
            default:
                throw new IllegalStateException("Error Interno.");
        }
        return zombiSeleccionado;
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