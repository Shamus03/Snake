import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeGui extends JFrame
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private SnakeGame game;
    private JPanel[][] cells;

    private int gameWidth;
    private int gameHeight;

    private boolean paused;

    public static void main(String[] args)
    {
        SnakeGui window = new SnakeGui();
        window.setVisible(true);
    }

    public SnakeGui()
    {
        super("Snake");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        game = new SnakeGame();
        gameWidth = game.getWidth();
        gameHeight = game.getHeight();
        paused = false;

        setLayout(new GridLayout(gameHeight, gameWidth));
        cells = new JPanel[gameHeight][gameWidth];

        int i;
        int j;
        for (i = 0; i < gameHeight; i++)
        {
            for (j = 0; j < gameWidth; j++)
            {
                cells[i][j] = new JPanel();
                add(cells[i][j]);
            }
        }

        addKeyListener(new SnakeAdapter());

        Timer timer = new Timer();
        TimerTask tickTask = new TimerTask()
        {
            public void run()
            {
                if (!paused)
                {
                    game.tick();
                    updateCells();
                }
            }
        };

        timer.schedule(tickTask, 0, 200);

        updateCells();
    }

    private void updateCells()
    {
        int i;
        int j;
        for (i = 0; i < gameHeight; i++)
        {
            for (j = 0; j < gameWidth; j++)
            {
                if (game.get(i, j) > 0)
                {
                    cells[i][j].setBackground(Color.BLACK);
                }
                else if (game.get(i, j) < 0)
                {
                    cells[i][j].setBackground(Color.RED);
                }
                else
                {
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private class SnakeAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            int code = e.getKeyCode();

            switch (code)
            {
                case KeyEvent.VK_UP:
                    game.changeDirection(SnakeGame.Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    game.changeDirection(SnakeGame.Direction.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    game.changeDirection(SnakeGame.Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.changeDirection(SnakeGame.Direction.RIGHT);
                    break;
                case KeyEvent.VK_R:
                    paused = false;
                    game.resetBoard();
                    break;
                case KeyEvent.VK_P:
                    paused = !paused;
                    break;
            }
        }
    }
}
