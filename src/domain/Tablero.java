package domain;

public class Tablero {

    private int filas;
    private int columnas;
    private Celda[][] celdas;

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

    public void agregarPlanta(Planta planta, int fila, int columna){

    }

    public void agregarZombi(Zombi zombi, int fila, int columna){

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
}
