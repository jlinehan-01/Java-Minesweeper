package app.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomButtonHandler implements ActionListener
{
    private static final int MAX_WIDTH = 50;
    private static final int MAX_HEIGHT = 30;
    private static final int MIN_FIELD_VALUE = 1;

    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField numMinesField;
    private final Menu parent;

    public CustomButtonHandler(JTextField widthField, JTextField heightField, JTextField numMinesField, Menu parent)
    {
        this.widthField = widthField;
        this.heightField = heightField;
        this.numMinesField = numMinesField;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // parse fields to int
        if (!verifyInput(widthField.getText()) || !verifyInput(heightField.getText()) || !verifyInput(numMinesField.getText()))
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

    private boolean verifyInput(String text)
    {
        try
        {
            Integer.parseInt(text);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
