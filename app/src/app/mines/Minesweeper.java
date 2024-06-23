package app.mines;

import app.mines.gameMenu.GameMenuBar;
import ch.aplu.jgamegrid.*;

import java.awt.*;

public class Minesweeper extends GameGrid implements GGMouseListener
{
    public static final int LOSS = -1;
    public static final String TITLE = "Java Minesweeper";

    private static final int CELL_SIZE = 30;
    private static final int SIMULATION_PERIOD = 50;
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final Color GRID_COLOR = Color.BLACK;

    private final StatusBar statusBar;
    private final Board board;

    private boolean minesSet = false;
    private boolean alive = true;

    public Minesweeper(int width, int height, int numMines, int best)
    {
        super(width, height, CELL_SIZE, GRID_COLOR, false);
        simulationPeriod = SIMULATION_PERIOD;
        addMouseListener(this, GGMouse.lClick | GGMouse.rClick | GGMouse.lDClick);
        board = new Board(this, width, height, numMines);
        statusBar = new StatusBar(this, numMines, best);
        getFrame().setMenuBar(new GameMenuBar(this));
        getFrame().pack();
    }

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

    public void mineHit()
    {
        alive = false;
    }

    public Board getBoard()
    {
        return board;
    }

    public void close()
    {
        mineHit();
        stopGameThread();
        dispose();
    }

    private void initGame()
    {
        showStatusBar(true);
        setTitle(TITLE);
        show();
        addStatusBar(STATUS_BAR_HEIGHT);
    }

    private synchronized void onClick()
    {
        notify();
    }
}
