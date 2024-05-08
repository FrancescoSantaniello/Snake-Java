package snake.game;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import snake.models.*;

import java.awt.event.KeyEvent;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.Timer;
import java.awt.Font;

public class GamePanel extends JPanel implements ActionListener {
    private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 15);
    private final int[] GRID_SIZE_X = {Cell.CELL_SIZE, SnakeGUI.X - (3 * Cell.CELL_SIZE)};
    private final int[] GRID_SIZE_Y = {Cell.CELL_SIZE, SnakeGUI.Y - (3 * Cell.CELL_SIZE)};
    private static final int SNAKE_SPEED = 12;
    private final SnakeGUI win;
    private final Snake snake = new Snake();
    private Food food;
    private final Timer timerUpdate;
    private int points = 1;
    private final int[] increment = {0, 0};
    private Graphics g;

    @Override
    public void paintComponent(Graphics _g) {
        super.paintComponent(_g);
        g = _g;
        updateGame();
    }

    private void updateGame() {
        if (!g.getFont().equals(DEFAULT_FONT))
            g.setFont(DEFAULT_FONT);

        if (!timerUpdate.isRunning()) {
            g.setColor(Color.BLACK);
            g.drawString("Pausa", (GRID_SIZE_X[1] / 2), (GRID_SIZE_Y[1] / 2));
        }

        snake.advance(nextCells());

        if (ckLose()) {
            timerUpdate.stop();
            win.endGame(points);
        }

        if (eatenFood(food)) {
            points++;
            food = genFood();
            snake.addCells(nextCells());
        }

        drawGrid();
        drawSnake();
        drawFood();
        drawPoints();
    }

    private boolean ckLose() {
        Cell headCell = snake.getCells().getLast();

        for (int i = 0; i < snake.getSize() - 2; i++)
            if (snake.getCells().get(i).equals(headCell))
                return true;

        return (headCell.getY() < GRID_SIZE_Y[0] || headCell.getY() > GRID_SIZE_Y[1])
                || headCell.getX() < GRID_SIZE_X[0] || headCell.getX() > GRID_SIZE_Y[1];
    }

    private Cell nextCells() {
        Cell cell = new Cell();

        cell.setX(snake.getCells().getLast().getX() + increment[0] * Cell.CELL_SIZE);
        cell.setY(snake.getCells().getLast().getY() + increment[1] * Cell.CELL_SIZE);

        return cell;
    }

    private void drawPoints() {
        g.setColor(Color.decode("#F3FFFF"));
        g.drawString("Punteggio: " + points, (GRID_SIZE_X[1] / 2) - (Cell.CELL_SIZE / 2), (Cell.CELL_SIZE / 2) + 2);
    }

    private void drawGrid() {
        final int spessor_line = 4;

        ((Graphics2D) g).setStroke(new BasicStroke(spessor_line));
        g.setColor(Color.decode("#578A34"));

        g.drawLine(GRID_SIZE_Y[0] - spessor_line, GRID_SIZE_X[0] - spessor_line, GRID_SIZE_Y[0] - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line);
        g.drawLine(GRID_SIZE_Y[0] - spessor_line, GRID_SIZE_X[0] - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_Y[0] - spessor_line);

        g.drawLine(GRID_SIZE_Y[0] - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_Y[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line);
        g.drawLine(GRID_SIZE_Y[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_X[1] + Cell.CELL_SIZE - spessor_line, GRID_SIZE_Y[0] - spessor_line);
    }

    private void drawSnake() {
        g.setColor(Color.decode("#4370E4"));
        for (Cell cell : snake.getCells())
            g.fillOval(cell.getX(), cell.getY(), Cell.CELL_SIZE, Cell.CELL_SIZE);
    }

    private void drawFood() {
        g.setColor(Color.RED);
        g.fillOval(food.getCell().getX(), food.getCell().getY(), Cell.CELL_SIZE, Cell.CELL_SIZE);
    }

    public void move(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            repaint();

            if (timerUpdate.isRunning()) {
                timerUpdate.stop();
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}

                timerUpdate.start();
            }

        }

        if (timerUpdate.isRunning()) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (increment[0] == 0 && increment[1] == 1)
                    return;

                increment[0] = 0;
                increment[1] = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                if (increment[0] == 0 && increment[1] == -1)
                    return;

                increment[0] = 0;
                increment[1] = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                if (increment[0] == 1 && increment[1] == 0)
                    return;

                increment[0] = -1;
                increment[1] = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                if (increment[0] == -1 && increment[1] == 0)
                    return;

                increment[0] = 1;
                increment[1] = 0;
            }
        }
    }

    public Food genFood() {
        Random rand = new Random();
        Food _food = new Food();

        int x = rand.nextInt((GRID_SIZE_X[1] / Cell.CELL_SIZE) - (GRID_SIZE_X[0] / Cell.CELL_SIZE)) + (GRID_SIZE_X[0] / Cell.CELL_SIZE);
        int y = rand.nextInt((GRID_SIZE_Y[1] / Cell.CELL_SIZE) - (GRID_SIZE_Y[0] / Cell.CELL_SIZE)) + (GRID_SIZE_Y[0] / Cell.CELL_SIZE);
        _food.setCell(new Cell(x * Cell.CELL_SIZE, y * Cell.CELL_SIZE));

        while (eatenFood(_food)) {
            x = rand.nextInt((GRID_SIZE_X[1] / Cell.CELL_SIZE) - (GRID_SIZE_X[0] / Cell.CELL_SIZE)) + (GRID_SIZE_X[0] / Cell.CELL_SIZE);
            y = rand.nextInt((GRID_SIZE_Y[1] / Cell.CELL_SIZE) - (GRID_SIZE_Y[0] / Cell.CELL_SIZE)) + (GRID_SIZE_Y[0] / Cell.CELL_SIZE);
            _food.setCell(new Cell(x * Cell.CELL_SIZE, y * Cell.CELL_SIZE));
        }

        return _food;
    }

    public boolean eatenFood(Food _food) {
        return snake.getCells().get(snake.getSize() - 1).equals(_food.getCell());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void start() {
        snake.addCells(new Cell(GRID_SIZE_X[0], GRID_SIZE_Y[0]));
        food = genFood();
        timerUpdate.start();
    }

    public GamePanel(SnakeGUI _win) {
        if (_win == null)
            throw new IllegalArgumentException("Finestra non valida");

        setBackground(Color.decode("#AAD751"));

        win = _win;

        timerUpdate = new Timer(Math.round(1000f / SNAKE_SPEED), this);

        start();
    }
}
