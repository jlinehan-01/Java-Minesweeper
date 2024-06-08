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

    public Minesweeper(int width, int height, int mines)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
        addMouseListener(this, GGMouse.lClick | GGMouse.rClick | GGMouse.lDClick);
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
                Actor tile = new Tile();
                addActor(tile, new Location(x, y));
            }
        }
    }

    @Override
    public boolean mouseEvent(GGMouse ggMouse)
    {
        Location location = toLocationInGrid(ggMouse.getX(), ggMouse.getY());
        if (location != null)
        {
            Actor tile = getOneActorAt(location);
            tile.showNextSprite();
        }
        return true;
    }
}
