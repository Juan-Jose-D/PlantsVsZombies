package domain;

import presentation.PlantsVsZombiesGUI;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lanzaguisantes extends Planta{

    private Juego juego;
    private final double FRECUENCIA_DISPARO = 1.5;
    private final double DAÑO = 20;

    public Lanzaguisantes(Juego juego) {
        super(100, 300);
        this.juego = juego;
    }

    public void iniciarDisparo(){
        System.out.println("Disparo");
    }

    public String getNombre(){
        super.nombre = "lanzaguisantes";
        return nombre;
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero){
        scheduler.scheduleAtFixedRate(() -> {
        }, 0, 1500, TimeUnit.MILLISECONDS);
    }

    public String getImagePath() {
        return "src/resources/images/Lanzaguisantes.png";
    }
}
