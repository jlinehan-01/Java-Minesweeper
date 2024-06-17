package app.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionEventHandler implements ActionListener
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
    private boolean buttonPressed = false;

    public ActionEventHandler(Menu parent)
    {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        buttonPressed = true;
        switch (e.getActionCommand())
        {
            case Menu.EASY_BUTTON_TEXT:
                parent.setNumMines(EASY_NUM_MINES);
                parent.setBoardHeight(EASY_HEIGHT);
                parent.setBoardWidth(EASY_WIDTH);
                break;
            case Menu.MEDIUM_BUTTON_TEXT:
                parent.setNumMines(MEDIUM_NUM_MINES);
                parent.setBoardHeight(MEDIUM_HEIGHT);
                parent.setBoardWidth(MEDIUM_WIDTH);
                break;
            case Menu.HARD_BUTTON_TEXT:
                parent.setNumMines(HARD_NUM_MINES);
                parent.setBoardHeight(HARD_HEIGHT);
                parent.setBoardWidth(HARD_WIDTH);
                break;
        }
        parent.boardSet();
    }

    public boolean buttonPressed()
    {
        return buttonPressed;
    }
}
