package app;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu implements ActionListener
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
    private final JFrame frame;

    public Menu(String title)
    {
        frame = new JFrame(title);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titlePanel.add(titleLabel);

        JPanel difficultyButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        difficultyButtonPanel.add(easyButton);
        difficultyButtonPanel.add(mediumButton);
        difficultyButtonPanel.add(hardButton);

        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);

        Component topMargin = Box.createRigidArea(new Dimension(261, 47));
        Component bottomMargin = Box.createRigidArea(new Dimension(261, 47));

        JPanel difficultyButtonPanelHolder = new JPanel(new BorderLayout());
        difficultyButtonPanelHolder.add(difficultyButtonPanel, BorderLayout.CENTER);
        difficultyButtonPanelHolder.add(topMargin, BorderLayout.NORTH);
        difficultyButtonPanelHolder.add(bottomMargin, BorderLayout.SOUTH);

        backgroundPanel.add(titlePanel);
        backgroundPanel.add(difficultyButtonPanelHolder);

        titlePanel.setPreferredSize(new Dimension(261, 135));

        frame.add(backgroundPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println(difficultyButtonPanelHolder.getSize());
        System.out.println(titlePanel.getSize());
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
    public void close()
    {
        frame.dispose();
    }
}
