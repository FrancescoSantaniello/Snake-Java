package snake;
import snake.game.SnakeGUI;
import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGUI::new);
    }
}
