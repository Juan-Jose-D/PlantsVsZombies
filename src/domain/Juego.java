package domain;

import presentation.PlantsVsZombiesGUI;

import java.io.ObjectStreamException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.ArrayList;

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
            case "Player Vs Machine":
                playerVsMachine();
                break;

            case "Machine Vs Machine":
                break;

            case "Player Vs Player":
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
        }, 0, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(this::moverZombi, 2, 2, TimeUnit.SECONDS);
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

    public void moverZombi() {
        List<int[]> movimientos = new ArrayList<>();

        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int columna = tablero.getColumnas() - 1; columna >= 0; columna--) {
                Object contenido = tablero.getZombi(fila, columna);

                if (contenido instanceof Zombi) {
                    int nuevaColumna = columna - 1;

                    if (nuevaColumna < 0) {
                        System.out.println("Fin del juego");
                        finish();
                        return;
                    }

                    if (tablero.isEmpty(fila, nuevaColumna)) {
                        movimientos.add(new int[]{fila, columna, nuevaColumna});
                    }
                }
            }
            ataqueZombi();
        }

        for (int[] movimiento : movimientos) {
            int fila = movimiento[0];
            int columna = movimiento[1];
            int nuevaColumna = movimiento[2];

            Zombi zombi = (Zombi) tablero.getZombi(fila, columna);
            plantsVsZombiesGUI.actualizarCeldaZombi(fila, nuevaColumna, zombi);
            tablero.moverZombi(fila, columna, nuevaColumna);
            plantsVsZombiesGUI.actualizarVista(tablero);
        }
    }

    public void ataqueZombi() {
        for (int fila = 0; fila < tablero.getFilas(); fila++) {
            for (int columna = tablero.getColumnas() - 1; columna >= 0; columna--) {
                Object contenido = tablero.getZombi(fila, columna);

                if (contenido instanceof Zombi) {
                    Zombi zombi = (Zombi) contenido;

                    int nuevaColumna = columna - 1;

                    if (nuevaColumna >= 0) {
                        Object planta = tablero.getPlanta(fila, nuevaColumna);

                        if (planta instanceof Planta) {
                            Planta plantaObjetivo = (Planta) planta;

                            plantaObjetivo.recibirDaño(zombi.getDaño());

                            if (plantaObjetivo.getVida() <= 0) {
                                plantaObjetivo.morir(fila, nuevaColumna, tablero, plantsVsZombiesGUI);
                                plantsVsZombiesGUI.actualizarVista(tablero);
                            }
                        }
                    }
                }
            }
        }
    }


    public void actualizarEstado() {
        plantsVsZombiesGUI.actualizarVista(tablero);
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