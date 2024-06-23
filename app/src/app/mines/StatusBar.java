package app.mines;

import app.ScoreHandler;

public class StatusBar implements Runnable
{
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int REFRESH_RATE = 500;

    private final Minesweeper game;
    private final Board board;
    private final int numMines;
    private final boolean havePreviousBest;
    private final String bestStr;

    private int best;
    private long startTime;
    private boolean timerStarted = false;
    private boolean gameOver = false;

    public StatusBar(Minesweeper game, int numMines, int best)
    {
        this.game = game;
        this.numMines = numMines;
        this.best = best;
        if (best == ScoreHandler.NO_BEST)
        {
            havePreviousBest = false;
            bestStr = "";
        }
        else
        {
            havePreviousBest = true;
            bestStr = " Best: ";
        }

        board = game.getBoard();
        game.addStatusBar(STATUS_BAR_HEIGHT);
        game.setStatusText("Mines: " + numMines + " Time: 0" + getBestStr());
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
        int score = (int) ((System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime);
        setText(score);
        return score;
    }

    public synchronized void update()
    {
        notify();
    }

    private void setText()
    {
        long duration = (System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime;
        game.setStatusText("Mines: " + (numMines - board.getNumFlags()) + " Time: " + duration + getBestStr());
    }

    private void setText(int score)
    {
        if (score < best)
        {
            best = score;
        }
        game.setStatusText("Mines: " + (numMines - board.getNumFlags()) + " Time: " + score + getBestStr());
    }

    private String getBestStr()
    {
        if (havePreviousBest)
        {
            return bestStr + best;
        }
        else
        {
            return bestStr;
        }
    }
}
