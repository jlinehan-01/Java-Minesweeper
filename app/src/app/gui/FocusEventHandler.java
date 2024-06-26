package app.gui;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Handles clearing hint text from text fields
 *
 * @author Joshua Linehan
 */
public class FocusEventHandler implements FocusListener
{
    private static final String EMPTY = "";

    /**
     * Clears the text from a text field when it is focused
     *
     * @param e the event to be processed
     */
    @Override
    public void focusGained(FocusEvent e)
    {
        JTextField field = (JTextField) e.getComponent();
        field.setText(EMPTY);
    }

    /**
     * Called when a text field loses focus; does nothing
     *
     * @param e the event to be processed
     */
    @Override
    public void focusLost(FocusEvent e)
    {
    }
}
