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
        Object zombie = getZombie(row, column);
        if (zombie instanceof Zombie) {
            elements[row][newColumn].setContent(zombie);
            elements[row][column].setContent(null);
        }
    }

    public void addPlant(Plant plant, int row, int column){
        elements[row][column].setContent(plant);
    }

    public void addZombi(Zombie zombie, int row, int column){
        elements[row][column].setContent(zombie);
    }

    public boolean isEmpty(int row, int column){
        return elements[row][column].getContent() == null;
    }

    public Element getElement(int row, int column) {
        return elements[row][column];
    }

    public void setElement(int row, int column, Object object) {
        elements[row][column].setContent(object);
    }
    
    public void eraseElement(int row, int column){
        elements[row][column].setContent(null);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Object getPlant(int row, int column) {
        return elements[row][column].getContent();
    }

    public Object getZombie(int row, int column) {
        return elements[row][column].getContent();
    }

    public int getRowObject(Object object){
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (elements[row][column].getContent() != null){
                    if (elements[row][column].getContent().equals(object)) {
                        return row;
                    }
                }
            }
        }
        return -1;
    }


}
