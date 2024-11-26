package domain;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import presentation.PlantsVsZombiesGUI;

public class Girasol extends Planta {
    private static final int FRECUENCIA_GENERACION = 20;
    private static final int SOLES_GENERADOS = 25;

    private Juego juego;
    private boolean solDisponible;
    private int fila;
    private int columna;
    private PlantsVsZombiesGUI gui;

    public Girasol(Juego juego) {
        super(50, 300);
        this.juego = juego;
        this.solDisponible = false;
    }

    public void setPosition(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public boolean tieneSolDisponible() {
        return solDisponible;
    }

    public void recolectarSol() {
        if (solDisponible) {
            juego.agregarSoles(SOLES_GENERADOS);
            solDisponible = false;
        }
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero) {
        this.gui = gui;
        scheduler.scheduleAtFixedRate(() -> {
            if (!solDisponible) {
                solDisponible = true;
                gui.iluminarCeldaGirasol(fila, columna);
            }
        }, FRECUENCIA_GENERACION, FRECUENCIA_GENERACION, TimeUnit.SECONDS);
    }

    public String getNombre() {
        return "girasol";
    }

    public String getImagePath() {
        return "src/resources/images/girasol.png";
    }



}
