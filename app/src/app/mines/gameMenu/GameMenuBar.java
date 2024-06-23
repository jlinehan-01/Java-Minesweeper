package app.mines.gameMenu;

import app.mines.Minesweeper;

import java.awt.*;

public class GameMenuBar extends MenuBar
{
    public GameMenuBar(Minesweeper game)
    {
        Menu menu = new Menu("Menu");
        MenuItem restartButton = new MenuItem("Restart");
        restartButton.addActionListener(new RestartHandler(game));
        menu.add(restartButton);
        add(menu);
    }
}
