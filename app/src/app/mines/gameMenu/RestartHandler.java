package app.mines.gameMenu;

import app.Driver;
import app.mines.Minesweeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles use of the New Game button
 *
 * @author Joshua Linehan
 */
public class RestartHandler implements ActionListener
{
    private final Minesweeper game;

    /**
     * Registers a Minesweeper instance to a new instance of RestartHandler
     *
     * @param game The current Minesweeper instance
     */
    public RestartHandler(Minesweeper game)
    {
        this.game = game;
    }

    /**
     * Closes the current game and starts a new Driver thread
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        game.close();
        Thread newGameThread = new Thread(new Driver());
        newGameThread.start();
    }
}
