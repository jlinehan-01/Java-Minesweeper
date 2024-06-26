package app;

import app.gui.Menu;
import app.mines.Minesweeper;

/**
 * Handles creation of the GUI and Minesweeper windows as well as communicating with ScoreHandler
 *
 * @author Joshua Linehan
 */
public class Driver implements Runnable
{
    /**
     * The method run when a driver thread is started. Creates a GUI window, creates a Minesweeper instance based on GUI
     * input, and passes Minesweeper result to ScoreHandler
     */
    @Override
    public void run()
    {
        Menu menu = new Menu(Minesweeper.TITLE);
        int numMines = menu.getNumMines();
        int height = menu.getBoardHeight();
        int width = menu.getBoardWidth();
        menu.close();

        ScoreHandler scoreHandler = new ScoreHandler();
        int best = scoreHandler.getBest(width, height, numMines);

        Minesweeper game = new Minesweeper(width, height, numMines, best);
        int result = game.runGame();
        scoreHandler.handleResult(width, height, numMines, result);
    }
}
