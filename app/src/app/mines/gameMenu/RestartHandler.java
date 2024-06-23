package app.mines.gameMenu;

import app.Driver;
import app.mines.Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartHandler implements ActionListener
{
    private final Minesweeper game;

    public RestartHandler(Minesweeper game)
    {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        game.close();
        Thread newGameThread = new Thread(new Driver());
        newGameThread.start();
    }
}
