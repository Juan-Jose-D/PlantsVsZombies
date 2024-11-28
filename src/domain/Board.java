package domain;

public class Board {

    private final int rows;
    private final int columns;
    private final Element[][] elements;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        elements = new Element[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                elements[i][j] = new Element();
            }
        }
    }


    public void moveZombie(int row, int column, int newColumn) {
        Object zombi = getZombie(row, column);
        if (zombi instanceof Zombie) {
            elements[row][newColumn].setContenido(zombi);
            elements[row][column].setContenido(null);
        }
    }

    public void addPlant(Plant plant, int row, int column){
        elements[row][column].setContenido(plant);
    }

    public void addZombi(Zombie zombie, int row, int column){
        elements[row][column].setContenido(zombie);
    }

    public boolean isEmpty(int row, int column){
        return elements[row][column].getContenido() == null;
    }

    public Element getElement(int row, int column) {
        return elements[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Object getPlant(int row, int column) {
        return elements[row][column].getContenido();
    }

    public Object getZombie(int row, int column) {
        return elements[row][column].getContenido();
    }

    public int getRowObject(Object object){
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (elements[row][column].getContenido() != null){
                    if (elements[row][column].getContenido().equals(object)) {
                        return row;
                    }
                }
            }
        }
        return -1;
    }


}
