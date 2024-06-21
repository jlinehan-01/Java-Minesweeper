package app.mines;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.Random;

public class Minesweeper extends GameGrid implements GGMouseListener
{
    public static final int LOSS = -1;
    public static final String TITLE = "Java Minesweeper";
    private static final int CELL_SIZE = 30;
    private static final int SIMULATION_PERIOD = 50;
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.lightGray;
    private final int numMines;
    private final int target;
    private final Random random = new Random();
    private final Tile[][] board;
    private final StatusBar statusBar;
    private boolean minesSet = false;
    private boolean alive = true;
    private int tilesOpened = 0;

    public Minesweeper(int width, int height, int numMines)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
        assert (numMines < (width * height));
        simulationPeriod = SIMULATION_PERIOD;
        this.numMines = numMines;
        addMouseListener(this, GGMouse.lClick | GGMouse.rClick | GGMouse.lDClick);
        board = new Tile[height][width];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                board[y][x] = new Tile();
            }
        }
        target = (height * width) - numMines;
        statusBar = new StatusBar(this, numMines);
    }

    public synchronized int runGame()
    {
        initGame();
        doRun();
        Thread thread = new Thread(statusBar);
        thread.start();
        while (alive)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            if (gameWon())
            {
                break;
            }
        }
        removeMouseListener(this);
        int score = statusBar.stopTimer();
        if (alive)
        {
            setFlags();
            return score;
        }
        else
        {
            showMines();
            return LOSS;
        }
    }

    @Override
    public boolean mouseEvent(GGMouse ggMouse)
    {
        Location location = toLocationInGrid(ggMouse.getX(), ggMouse.getY());
        if (location != null)
        {
            switch (ggMouse.getEvent())
            {
                case GGMouse.lClick:
                    if (minesSet)
                    {
                        board[location.getY()][location.getX()].open(board, this);
                    }
                    else
                    {
                        setMines(location);
                        statusBar.startTimer();
                    }
                case GGMouse.rClick:
                    board[location.getY()][location.getX()].flag(this);
                case GGMouse.lDClick:
                    board[location.getY()][location.getX()].clear(board, this);
            }
        }
        onClick();
        return true;
    }

    public void mineHit()
    {
        alive = false;
    }

    public void addFlag()
    {
        statusBar.addFlag();
    }

    public void removeFlag()
    {
        statusBar.removeFlag();
    }

    public void tileOpened()
    {
        tilesOpened++;
    }

    private void initGame()
    {
        showStatusBar(true);
        setTitle(TITLE);
        generateTiles();
        show();
        addStatusBar(STATUS_BAR_HEIGHT);
    }

    private void generateTiles()
    {
        for (int y = 0; y < getNbVertCells(); y++)
        {
            for (int x = 0; x < getNbHorzCells(); x++)
            {
                addActor(board[y][x], new Location(x, y));
            }
        }
    }

    private void setMines(Location location)
    {
        // place mines
        for (int i = 0; i < numMines; )
        {
            int x = random.nextInt(getNbHorzCells());
            int y = random.nextInt(getNbVertCells());
            if (board[y][x].containsMine() || (x == location.getX() && y == location.getY()))
            {
                continue;
            }
            board[y][x].setMine();
            i++;
        }
        // calculate numbers
        for (int y = 0; y < getNbVertCells(); y++)
        {
            for (int x = 0; x < getNbHorzCells(); x++)
            {
                board[y][x].calculateSurroundingMines(board);
            }
        }
        board[location.getY()][location.getX()].open(board, this);
        minesSet = true;
    }

    private synchronized void onClick()
    {
        notify();
    }

    private void showMines()
    {
        for (Tile[] row : board)
        {
            for (Tile tile : row)
            {
                tile.showMine();
            }
        }
    }

    private boolean gameWon()
    {
        return tilesOpened == target;
    }

    private void setFlags()
    {
        for (Tile[] row : board)
        {
            for (Tile tile : row)
            {
                if (tile.containsMine() && !tile.isFlagged())
                {
                    tile.flag(this);
                }
            }
        }
    }
}
