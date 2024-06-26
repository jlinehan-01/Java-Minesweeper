package app.mines.gameMenu;

import app.mines.Minesweeper;

import java.awt.*;

/**
 * The in-game menu at the top of the window
 *
 * @author Joshua Linehan
 */
public class GameMenuBar extends MenuBar
{
    /**
     * Creates a GameMenuBar
     *
     * @param game The Minesweeper instance this is added to
     */
    public GameMenuBar(Minesweeper game)
    {
        Menu menu = new Menu("Menu");
        MenuItem restartButton = new MenuItem("New Game");
        restartButton.addActionListener(new RestartHandler(game));
        menu.add(restartButton);
        add(menu);
    }
}
