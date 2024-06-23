package app;

import app.gui.Menu;
import app.mines.Minesweeper;

public class Driver implements Runnable
{
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
