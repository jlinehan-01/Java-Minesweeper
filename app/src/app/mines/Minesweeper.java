package app.mines;

import app.mines.gameMenu.GameMenuBar;
import ch.aplu.jgamegrid.*;

import java.awt.*;

/**
 * A game of minesweeper
 *
 * @author Joshua Linehan
 */
public class Minesweeper extends GameGrid implements GGMouseListener
{
    /**
     * Integer that replaces the returned score to indicate that the game has been lost
     */
    public static final int LOSS = -1;
    /**
     * The title of the game
     */
    public static final String TITLE = "Java Minesweeper";

    private static final int CELL_SIZE = 30;
    private static final int SIMULATION_PERIOD = 50;
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final Color GRID_COLOR = Color.BLACK;

    private final StatusBar statusBar;
    private final Board board;

    private boolean minesSet = false;
    private boolean alive = true;

    /**
     * Creates a Minesweeper game with the given specifications
     *
     * @param width    The width of the Board in Tiles
     * @param height   The height of the Board in Tiles
     * @param numMines The number of mines on the Board
     * @param best     The previous best time to solve a board with the same parameters
     */
    public Minesweeper(int width, int height, int numMines, int best)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
        simulationPeriod = SIMULATION_PERIOD;
        board = new Board(this, width, height, numMines);
        statusBar = new StatusBar(this, numMines, best);
        addMouseListener(this, GGMouse.lClick | GGMouse.rClick | GGMouse.lDClick);
        getFrame().setMenuBar(new GameMenuBar(this));
        getFrame().pack();
    }

    /**
     * Starts the game
     *
     * @return The result of the game; either the score in seconds taken to solve, or
     * {@link #LOSS an integer representing a loss}
     */
    public synchronized int runGame()
    {
        initGame();
        doRun();
        Thread statusBarThread = new Thread(statusBar);
        statusBarThread.start();
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
            if (board.isComplete())
            {
                break;
            }
        }
        removeMouseListener(this);
        int score = statusBar.stopTimer();
        if (alive)
        {
            playSound(GGSound.NOTIFY);
            board.setFlags();
            return score;
        }
        else
        {
            board.showMines();
            return LOSS;
        }
    }

    /**
     * Triggers game events based on mouse input
     *
     * @param ggMouse The mouse input event
     * @return true if the mouse event is consumed, which it always is
     */
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
                        board.open(location);
                    }
                    else
                    {
                        board.setMines(location);
                        minesSet = true;
                        statusBar.startTimer();
                    }
                    break;
                case GGMouse.rClick:
                    board.flag(location);
                    statusBar.update();
                    break;
                case GGMouse.lDClick:
                    board.clear(location);
                    break;
            }
        }
        onClick();
        return true;
    }

    /**
     * Records that a mine has been hit and the game has been lost
     */
    public void mineHit()
    {
        alive = false;
    }

    /**
     * Gets the game's Board
     *
     * @return the game's Board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Ends the current game and closes the window
     */
    public void close()
    {
        mineHit();
        stopGameThread();
        dispose();
    }

    /**
     * Sets up the game window
     */
    private void initGame()
    {
        showStatusBar(true);
        setTitle(TITLE);
        show();
        addStatusBar(STATUS_BAR_HEIGHT);
    }

    /**
     * Wakes the game thread when called after each click
     */
    private synchronized void onClick()
    {
        notify();
    }
}
