package app.mines;

import ch.aplu.jgamegrid.*;

import java.awt.*;

public class Minesweeper extends GameGrid
{
    private final static int CELL_SIZE = 30;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.lightGray;
    private static final String TITLE = "Java Minesweeper";

    public Minesweeper(int width, int height, int mines)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
    }
    public void runGame()
    {
        initGame();
    }
    private void initGame()
    {
        showStatusBar(true);
        setTitle(TITLE);
        show();
        generateTiles();
        renderBackground();
    }
    private void renderBackground()
    {
        GGBackground bg = getBg();
        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getHeight(); x++)
            {
                bg.fillCell(new Location(x, y), BACKGROUND_COLOR);
            }
        }
        bg.drawGridLines(GRID_COLOR);
    }
    private void generateTiles()
    {
        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                Actor tile = new Actor("unopened.png");
                addActor(tile, new Location(x,y));
            }
        }
    }
}
