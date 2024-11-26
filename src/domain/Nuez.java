package domain;

import presentation.PlantsVsZombiesGUI;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Nuez extends Planta {

    private Juego juego;

    public Nuez(Juego juego) {
        super(50, 4000);
        this.juego = juego;

    }

    public String getNombre() {
        super.nombre = "nuez";
        return nombre;
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero) {
        scheduler.scheduleAtFixedRate(() -> {
        }, 0, 20, TimeUnit.SECONDS);
    }

    public String getImagePath() {
        return "src/resources/images/nuez.png";
    }
}
