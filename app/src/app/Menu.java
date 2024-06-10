package app;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener
{
    private static final int HEIGHT = 310;
    private static final int WIDTH = 287;
    private static final int EASY_NUM_MINES = 10;
    private static final int EASY_WIDTH = 9;
    private static final int EASY_HEIGHT = 9;
    private static final int MEDIUM_NUM_MINES = 40;
    private static final int MEDIUM_WIDTH = 16;
    private static final int MEDIUM_HEIGHT = 16;
    private static final int HARD_NUM_MINES = 99;
    private static final int HARD_WIDTH = 30;
    private static final int HARD_HEIGHT = 16;
    private static final String EASY_BUTTON_TEXT = "Easy";
    private static final String MEDIUM_BUTTON_TEXT = "Medium";
    private static final String HARD_BUTTON_TEXT = "Hard";

    private boolean buttonPressed = false;
    private int boardHeight;
    private int boardWidth;
    private int numMines;

    public Menu(String title)
    {
        super(title);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(2, 3));

        JLabel label = new JLabel(title, JLabel.CENTER);
        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(this);
        JButton mediumButton = new JButton("Medium");
        mediumButton.addActionListener(this);
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(this);
        JLabel topLeft = new JLabel();
        JLabel topRight = new JLabel();

        panel.add(topLeft);
        panel.add(label);
        panel.add(topRight);
        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e)
    {
        buttonPressed = true;
        switch (e.getActionCommand())
        {
            case EASY_BUTTON_TEXT:
                numMines = EASY_NUM_MINES;
                boardHeight = EASY_HEIGHT;
                boardWidth = EASY_WIDTH;
                break;
            case MEDIUM_BUTTON_TEXT:
                numMines = MEDIUM_NUM_MINES;
                boardHeight = MEDIUM_HEIGHT;
                boardWidth = MEDIUM_WIDTH;
                break;
            case HARD_BUTTON_TEXT:
                numMines = HARD_NUM_MINES;
                boardHeight = HARD_HEIGHT;
                boardWidth = HARD_WIDTH;
                break;
        }
        notifyAll();
    }
    public int getNumMines()
    {
        waitForButtonPress();
        return numMines;
    }
    public int getBoardHeight()
    {
        waitForButtonPress();
        return boardHeight;
    }
    public int getBoardWidth()
    {
        waitForButtonPress();
        return boardWidth;
    }
    private synchronized void waitForButtonPress()
    {
        if (!buttonPressed)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
