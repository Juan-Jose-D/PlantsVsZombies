package domain;

import presentation.PlantsVsZombiesGUI;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Lanzaguisantes extends Planta{

    private Juego juego;
    private final int FRECUENCIA_DISPARO = 1500;
    private final int DAÑO = 20;

    public Lanzaguisantes(Juego juego) {
        super(100, 300);
        this.juego = juego;
    }


    public void atacar(int fila, PlantsVsZombiesGUI gui, Tablero tablero) {
        for (int columna = 0; columna < tablero.getColumnas(); columna++){
            if (tablero.getCelda(fila, columna).getContenido() instanceof Zombi){
                Zombi zombi = (Zombi) tablero.getCelda(fila, columna).getContenido();
                zombi.recibirDaño(DAÑO);
                if (zombi.getVida() <= 0){
                    zombi.morir(fila, columna, tablero, gui);
                }
            }
        }
    }


    public String getNombre(){
        super.nombre = "lanzaguisantes";
        return nombre;
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero){
    }

    public void iniciarAcciones(ScheduledExecutorService scheduler, PlantsVsZombiesGUI gui, Tablero tablero, int fila){
        scheduler.scheduleAtFixedRate(() -> {
            atacar(fila, gui, tablero);
        }, FRECUENCIA_DISPARO, FRECUENCIA_DISPARO, TimeUnit.MILLISECONDS);
    }

    public String getImagePath() {
        return "src/resources/images/Lanzaguisantes.png";
    }
}
