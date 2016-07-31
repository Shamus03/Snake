import java.util.Random;

public class SnakeGame
{
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_HEIGHT = 30;
    private static final int DEFAULT_LENGTH = 3;

    private static Random generator = new Random();

    private int width;
    private int height;

    private boolean loop;

    private int headRow;
    private int headCol;
    private int length;
    private Direction traveling;
    private Direction lastTraveling;

    private boolean alive;

    private int[][] board;

    public enum Direction
    {
        UP, RIGHT, DOWN, LEFT
    }

    public static void main(String[] args)
    {
        SnakeGame game = new SnakeGame();
        System.out.println(game);
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        System.out.println(game);
    }

    public SnakeGame(int width, int height)
    {
        this.width = width;
        this.height = height;

        loop = false;

        resetBoard();
    }

    public SnakeGame()
    {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public void tick()
    {
        if (alive)
        {
            lastTraveling = traveling;
            switch(traveling)
            {
                case UP:
                    headRow--;
                    break;
                case DOWN:
                    headRow++;
                    break;
                case LEFT:
                    headCol--;
                    break;
                case RIGHT:
                    headCol++;
                    break;
            }

            if (loop)
            {
                headRow = (headRow + height) % height;
                headCol = (headCol + width) % width;
            }

            System.out.println(headRow);

            if (headRow < 0 || headRow >= height
                || headCol < 0 || headCol >= width
                || (board[headRow][headCol] > 0
                    && board[headRow][headCol] < length))
            {
                alive = false;
            }
            else
            {
                if (board[headRow][headCol] == -1)
                {
                    length++;
                    placeRandomPickup();
                }

                int i;
                int j;
                for (i = 0; i < height; i++)
                {
                    for (j = 0; j < width; j++)
                    {
                        if (board[i][j] >= length)
                        {
                            board[i][j] = 0;
                        }
                        else if (board[i][j] > 0)
                        {
                            board[i][j]++;
                        }
                    }
                }
                board[headRow][headCol] = 1;
            }
        }
    }

    public int get(int row, int col)
    {
        return board[row][col];
    }

    public void changeDirection(Direction dir)
    {
        if ((lastTraveling.ordinal() + dir.ordinal()) % 2 == 1) //no suicide
            traveling = dir;
    }

    public void resetBoard()
    {
        alive = true;
        length = DEFAULT_LENGTH;
        board = new int[height][width];

        headRow = height / 2;
        headCol = width / 2;
        board[headRow][headCol] = 1;
        traveling = Direction.UP;

        placeRandomPickup();
    }

    private void placeRandomPickup()
    {
        boolean placed = false;

        while (!placed)
        {
            int row = generator.nextInt(height);
            int column = generator.nextInt(width);

            if (board[row][column] == 0)
            {
                board[row][column] = -1;
                placed = true;
            }
        }
    }

    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }

    public String toString()
    {
        String result = "";
        int i;
        int j;
        for (i = 0; i < height; i++)
        {
            for (j = 0; j < width; j++)
            {
                result += board[i][j] + " ";
            }
            result += "\n";
        }

        result += traveling;

        return result;
    }
}
