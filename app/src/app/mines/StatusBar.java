package app.mines;

import app.ScoreHandler;

/**
 * The status bar that appears below the game window
 *
 * @author Joshua Linehan
 */
public class StatusBar implements Runnable
{
    private static final int STATUS_BAR_HEIGHT = 30;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int REFRESH_RATE = 500;
    private static final String TUTORIAL_TEXT = "Left click: Open tile | Right click: Flag tile | Double click: Clear surrounding tiles";

    private final Minesweeper game;
    private final Board board;
    private final int numMines;
    private final boolean havePreviousBest;
    private final String bestStr;

    private int best;
    private long startTime;
    private boolean timerStarted = false;
    private boolean gameOver = false;

    /**
     * Creates a StatusBar with the given information and adds it to the game's window
     *
     * @param game     The Minesweeper instance the StatusBar belongs to
     * @param numMines The number of mines on the board
     * @param best     The previous best time for the game's board
     */
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
        game.setStatusText(TUTORIAL_TEXT);
    }

    /**
     * Called when the thread is started. Displays the current time and mine count
     */
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

    /**
     * Sets the game start time
     */
    public void startTimer()
    {
        startTime = System.currentTimeMillis() / MILLISECONDS_PER_SECOND;
        timerStarted = true;
    }

    /**
     * Flags the thread to stop and finds the final time
     *
     * @return The game duration when the timer was stopped
     */
    public int stopTimer()
    {
        gameOver = true;
        int score = (int) ((System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime);
        setText(score);
        return score;
    }

    /**
     * Wakes the thread to update the status bar's text
     */
    public synchronized void update()
    {
        notify();
    }

    /**
     * Sets the text displayed on the status bar
     */
    private void setText()
    {
        long duration = (System.currentTimeMillis() / MILLISECONDS_PER_SECOND) - startTime;
        game.setStatusText("Mines: " + (numMines - board.getNumFlags()) + " Time: " + duration + getBestStr());
    }

    /**
     * Sets the text on the status bar. Called when the game is stopped and sets the displayed time to the final time
     *
     * @param score The game duration when the game was stopped
     */
    private void setText(int score)
    {
        if (score < best)
        {
            best = score;
        }
        game.setStatusText("Mines: " + (numMines - board.getNumFlags()) + " Time: " + score + getBestStr());
    }

    /**
     * Generates the String to use to represent the previous best score for the game's Board, based on if one exists
     *
     * @return The best String, which represents the previous best time and is empty if no best time was provided
     */
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
