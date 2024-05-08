package snake.game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TreeSet;

public class SnakeGUI extends JFrame {
    public static final int X = 600;
    public static final int Y = 600;
    private GamePanel gamePanel = new GamePanel(this);
    private final TreeSet<Integer> treeSetPoints = new TreeSet<>();
    private void listeners(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gamePanel.move(e);
            }
        });
    }
    public void endGame(int point){
        String msg = String.format("Hai perso :/\nPunti: %d\nVuoi fare un altra partita?", point);

        if (!treeSetPoints.isEmpty()) {
            int lastPoint = treeSetPoints.last();
            if (lastPoint < point)
                msg = String.format("Hai perso :/\nNuovo Record!\nPunti: %d\nVuoi fare un altra partita?", point);
            else
                msg = String.format("Hai perso :/\nPunteggio più alto: %d\nPunti: %d\nVuoi fare un altra partita?", lastPoint,point);
        }

        treeSetPoints.add(point);

        if (JOptionPane.showConfirmDialog(null, msg,getTitle(), JOptionPane.YES_NO_OPTION) == 0){
            remove(gamePanel);

            gamePanel = new GamePanel(this);
            add(gamePanel, BorderLayout.CENTER);

            revalidate();
            repaint();
        }
        else{
            System.exit(0);
        }
    }

    public SnakeGUI(){
        super("Snake");
        setSize(X,Y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        listeners();

        gamePanel = new GamePanel(this);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
