package snake.models;

public class Food {
    private Cell cell;
    public void setCell(Cell _cell){
        if (_cell == null)
            throw new IllegalArgumentException("Cella non valida");

        cell = _cell;
    }
    public Cell getCell(){
        return cell;
    }
    public Food(){}
}
