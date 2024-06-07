package app.mines;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.Random;

public class Minesweeper extends GameGrid
{
    private final static int CELL_SIZE = 30;
    private static final Color GRID_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.lightGray;
    private static final String TITLE = "Java Minesweeper";

    private Random rand = new Random();

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
        System.out.println("starting generateTiles");
        for (int y = 0; y < getNbVertCells(); y++)
        {
            for (int x = 0; x < getNbHorzCells(); x++)
            {
                String[] files = {"unopened.png", "mine.png", "empty.png", "hit.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png"};
                int i = rand.nextInt(files.length);
                Actor tile = new Actor(files[i]);
                addActor(tile, new Location(x,y));
            }
        }
    }
}
