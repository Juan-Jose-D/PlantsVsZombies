package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lanzaguisantes extends Planta{

    private final double FRECUENCIA_DISPARO = 1.5;
    private final double DAÑO = 20;

    public Lanzaguisantes() {
        super(100, 300);
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
}
