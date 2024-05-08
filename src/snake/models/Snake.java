package snake.models;
import java.util.List;
import java.util.ArrayList;
public class Snake {
    private List<Cell> cells = new ArrayList<>();
    public int getSize(){
        return cells.size();
    }
    public List<Cell> getCells(){
        return cells;
    }
    public void addCells(Cell newCell){
        if (newCell == null)
            return;

        cells.add(newCell);
    }
    public void advance(Cell cell){
        if (cell == null)
            return;

        cells.add(cell);
        cells.removeFirst();
    }
}
