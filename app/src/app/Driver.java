package app;

import app.gui.Menu;
import app.mines.Minesweeper;

public class Driver
{
    public static void main(String[] args)
    {
        Menu menu = new Menu(Minesweeper.TITLE);
        int numMines = menu.getNumMines();
        int height = menu.getBoardHeight();
        int width = menu.getBoardWidth();
        menu.close();
        Minesweeper game = new Minesweeper(width, height, numMines);
        int result = game.runGame();
        System.out.println(result);
    }
}
