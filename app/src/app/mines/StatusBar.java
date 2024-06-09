package app.mines;

import ch.aplu.jgamegrid.GameGrid;

public class StatusBar implements Runnable
{
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int REFRESH_RATE = 500;
    private final GameGrid game;
    private final int numMines;
    private long startTime;
    private int numFlags = 0;
    private boolean timerStarted = false;
    private boolean gameOver = false;

    public StatusBar(GameGrid game, int numMines)
    {
        this.game = game;
        this.numMines = numMines;
        game.addStatusBar(STATUS_BAR_HEIGHT);
        game.setStatusText("Mines: " + numMines + " Time: 0");
    }

    @Override
    public synchronized void run()
    {
        while (!gameOver)
        {
            if (timerStarted)
            {
                setText();
            }
            try
            {
                wait(REFRESH_RATE);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void startTimer()
    {
        startTime = System.currentTimeMillis() / MILLISECONDS_PER_SECOND;
        timerStarted = true;
    }

    public int stopTimer()
    {
        gameOver = true;
        return (int) ((System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime);
    }

    public synchronized void addFlag()
    {
        numFlags++;
        notify();
    }

    public synchronized void removeFlag()
    {
        numFlags--;
        notify();
    }

    private void setText()
    {
        long duration = (System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime;
        game.setStatusText("Mines: " + (numMines - numFlags) + " Time: " + duration);
    }
}
