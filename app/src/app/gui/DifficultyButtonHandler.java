package app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles use of the Easy, Medium, and Hard buttons in the menu
 *
 * @author Joshua Linehan
 */
public class DifficultyButtonHandler implements ActionListener
{
    private static final int EASY_NUM_MINES = 10;
    private static final int EASY_WIDTH = 9;
    private static final int EASY_HEIGHT = 9;
    private static final int MEDIUM_NUM_MINES = 40;
    private static final int MEDIUM_WIDTH = 16;
    private static final int MEDIUM_HEIGHT = 16;
    private static final int HARD_NUM_MINES = 99;
    private static final int HARD_WIDTH = 30;
    private static final int HARD_HEIGHT = 16;

    private final Menu parent;

    /**
     * Creates a DifficultyButtonHandler with a reference to the Menu instance
     *
     * @param parent The Menu instance which contains the difficulty buttons
     */
    public DifficultyButtonHandler(Menu parent)
    {
        this.parent = parent;
    }

    /**
     * Sets board to appropriate configuration in response to press of a specific difficulty button
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand())
        {
            case Menu.EASY_BUTTON_TEXT ->
            {
                parent.setNumMines(EASY_NUM_MINES);
                parent.setBoardHeight(EASY_HEIGHT);
                parent.setBoardWidth(EASY_WIDTH);
            }
            case Menu.MEDIUM_BUTTON_TEXT ->
            {
                parent.setNumMines(MEDIUM_NUM_MINES);
                parent.setBoardHeight(MEDIUM_HEIGHT);
                parent.setBoardWidth(MEDIUM_WIDTH);
            }
            case Menu.HARD_BUTTON_TEXT ->
            {
                parent.setNumMines(HARD_NUM_MINES);
                parent.setBoardHeight(HARD_HEIGHT);
                parent.setBoardWidth(HARD_WIDTH);
            }
        }
        parent.boardSet();
    }
}
