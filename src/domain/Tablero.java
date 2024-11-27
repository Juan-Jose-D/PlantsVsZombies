package domain;

import presentation.PlantsVsZombiesGUI;

import java.util.concurrent.ScheduledExecutorService;

public class Tablero {

    private int filas;
    private int columnas;
    private Celda[][] celdas;
    private PlantsVsZombiesGUI plantsVsZombiesGUI;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }


    public void moverZombi(int fila, int columna, int nuevaColumna) {
        Object zombi = getZombi(fila, columna);
        if (zombi instanceof Zombi) {
            celdas[fila][nuevaColumna].setContenido(zombi);
            celdas[fila][columna].setContenido(null);
        }
    }

    public void agregarPlanta(Planta planta, int fila, int columna){
        celdas[fila][columna].setContenido(planta);
    }

    public void agregarZombi(Zombi zombi, int fila, int columna){
        celdas[fila][columna].setContenido(zombi);
    }

    public boolean isEmpty(int fila, int columna){
        return celdas[fila][columna].getContenido() == null;
    }

    public Celda getCelda(int fila, int columna) {
        return celdas[fila][columna];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Object getPlanta(int fila, int columna) {
        return celdas[fila][columna].getContenido();
    }

    public Object getZombi(int fila, int columna) {
        return celdas[fila][columna].getContenido();
    }

    public int getFilaObject(Object object){
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                if (celdas[fila][columna].getContenido() != null){
                    if (celdas[fila][columna].getContenido().equals(object)) {
                        return fila;
                    }
                }
            }
        }
        return -1;
    }


}
