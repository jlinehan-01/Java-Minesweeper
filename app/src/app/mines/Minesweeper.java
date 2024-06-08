package app.mines;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.Random;

public class Minesweeper extends GameGrid implements GGMouseListener
{
    private final static int CELL_SIZE = 30;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.lightGray;
    private static final String TITLE = "Java Minesweeper";
    private final int numMines;
    private final Random random = new Random();
    private final Tile[][] board;
    private boolean minesSet = false;

    public Minesweeper(int width, int height, int numMines)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
        assert(numMines < (width * height));
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
    }

    public void runGame()
    {
        initGame();
        doRun();
    }

    private void initGame()
    {
        showStatusBar(true);
        setTitle(TITLE);
        renderBackground();
        generateTiles();
        show();
    }

    private void renderBackground()
    {
        GGBackground bg = getBg();
        for (int y = 0; y < getNbVertCells(); y++)
        {
            for (int x = 0; x < getNbHorzCells(); x++)
            {
                bg.fillCell(new Location(x, y), BACKGROUND_COLOR);
            }
        }
        bg.drawGridLines(GRID_COLOR);
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
                        board[location.getY()][location.getX()].open();
                    }
                    else
                    {
                        setMines(location);
                    }
                case GGMouse.rClick:
                    board[location.getY()][location.getX()].flag();
                case GGMouse.lDClick:
                    board[location.getY()][location.getX()].clear();
            }
        }
        return true;
    }
    private void setMines(Location location)
    {
        // place mines
        for (int i = 0; i < numMines;)
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
        board[location.getY()][location.getX()].open();
        minesSet = true;
    }
}
