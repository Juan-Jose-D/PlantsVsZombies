package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Nuez extends Planta{

    public Nuez(){
        super(50, 4000);
    }

    public String getNombre(){
        super.nombre = "nuez";
        return nombre;
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero){
        scheduler.scheduleAtFixedRate(() -> {
        }, 0, 20, TimeUnit.SECONDS);
    }
}
