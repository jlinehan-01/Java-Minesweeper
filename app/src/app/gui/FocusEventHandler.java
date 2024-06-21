package app.gui;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusEventHandler implements FocusListener
{
    private static final String EMPTY = "";

    @Override
    public void focusGained(FocusEvent e)
    {
        JTextField field = (JTextField) e.getComponent();
        field.setText(EMPTY);
    }

    @Override
    public void focusLost(FocusEvent e){}
}
