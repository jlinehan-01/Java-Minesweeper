package app.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles custom game input from GUI
 *
 * @author Joshua Linehan
 */
public class CustomButtonHandler implements ActionListener
{
    private static final int MAX_WIDTH = 50;
    private static final int MAX_HEIGHT = 30;
    private static final int MIN_FIELD_VALUE = 1;

    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField numMinesField;
    private final Menu parent;

    /**
     * Creates a CustomButtonHandler instance with records of the Menu and its text fields
     *
     * @param widthField    The width text field
     * @param heightField   The height text field
     * @param numMinesField The mines text field
     * @param parent        The Menu instance
     */
    public CustomButtonHandler(JTextField widthField, JTextField heightField, JTextField numMinesField, Menu parent)
    {
        this.widthField = widthField;
        this.heightField = heightField;
        this.numMinesField = numMinesField;
        this.parent = parent;
    }

    /**
     * Handles custom button press event
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // parse fields to int
        if (badInput(widthField.getText()) || badInput(heightField.getText()) || badInput(numMinesField.getText()))
        {
            parent.setErrorText("Values must be integers");
            return;
        }

        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int numMines = Integer.parseInt(numMinesField.getText());

        // check each input is valid
        if (width < MIN_FIELD_VALUE || height < MIN_FIELD_VALUE || numMines < MIN_FIELD_VALUE)
        {
            parent.setErrorText("Values must be positive");
            return;
        }
        if (width > MAX_WIDTH)
        {
            parent.setErrorText("Width cannot be greater than " + MAX_WIDTH);
            return;
        }
        if (height > MAX_HEIGHT)
        {
            parent.setErrorText("Height cannot be greater than " + MAX_HEIGHT);
            return;
        }
        int maxMines = (width * height) - 1;
        if (numMines > maxMines)
        {
            parent.setErrorText("Too many mines");
            return;
        }

        // send data to menu
        parent.setBoardWidth(width);
        parent.setBoardHeight(height);
        parent.setNumMines(numMines);
        parent.boardSet();
    }

    /**
     * Verifies input from a text field contains only numbers
     *
     * @param text Input obtained from a JTextField
     * @return true is text is invalid, false if text contains only numbers
     */
    private boolean badInput(String text)
    {
        try
        {
            Integer.parseInt(text);
            return false;
        }
        catch (NumberFormatException e)
        {
            return true;
        }
    }
}
