package snake.models;
public class Cell {
    public static final int CELL_SIZE = 25;
    private int x;
    private int y;
    @Override
    public boolean equals(Object c){
        if (!(c instanceof Cell))
            return false;

        Cell cell = (Cell)c;
        return x == cell.getX() && y == cell.getY();
    }
    public void setX(int _x){
        x = _x;
    }
    public void setY(int _y){
        y  = _y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Cell(int _x, int _y){
        setX(_x);
        setY(_y);
    }
    public Cell(){}
}
