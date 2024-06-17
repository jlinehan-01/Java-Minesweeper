package app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class Menu
{
    private static final int HEIGHT = 310;
    private static final int WIDTH = 287;
    private static final int FIELD_NUM_COLUMNS = 4;
    public static final String EASY_BUTTON_TEXT = "Easy";
    public static final String MEDIUM_BUTTON_TEXT = "Medium";
    public static final String HARD_BUTTON_TEXT = "Hard";
    public static final String WIDTH_FIELD_DEFAULT = "Width";
    public static final String HEIGHT_FIELD_DEFAULT = "Height";
    public static final String MINES_FIELD_DEFAULT = "Mines";

    private int boardHeight;
    private int boardWidth;
    private int numMines;
    private final ActionEventHandler buttonClickHandler;
    private final JFrame frame;
    private final JTextField widthField = new JTextField(WIDTH_FIELD_DEFAULT, FIELD_NUM_COLUMNS);
    private final JTextField heightField = new JTextField(HEIGHT_FIELD_DEFAULT, FIELD_NUM_COLUMNS);
    private final JTextField numMinesField = new JTextField(MINES_FIELD_DEFAULT, FIELD_NUM_COLUMNS);

    public Menu(String title)
    {
        buttonClickHandler = new ActionEventHandler(this);
        FocusEventHandler focusEventHandler = new FocusEventHandler();

        // initialise frame
        frame = new JFrame(title);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titlePanel.add(titleLabel);

        // difficulty buttons
        JPanel difficultyButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        difficultyButtonPanel.add(easyButton);
        difficultyButtonPanel.add(mediumButton);
        difficultyButtonPanel.add(hardButton);

        easyButton.addActionListener(buttonClickHandler);
        mediumButton.addActionListener(buttonClickHandler);
        hardButton.addActionListener(buttonClickHandler);

        // difficulty button holder
        JPanel difficultyButtonPanelHolder = new JPanel(new GridLayout(3, 1));
        difficultyButtonPanelHolder.add(new JPanel());
        difficultyButtonPanelHolder.add(difficultyButtonPanel, BorderLayout.CENTER);
        difficultyButtonPanelHolder.add(new JPanel());

        // custom game fields
        JPanel customGameFields = new JPanel(new FlowLayout(FlowLayout.CENTER));
        widthField.addFocusListener(focusEventHandler);
        heightField.addFocusListener(focusEventHandler);
        numMinesField.addFocusListener(focusEventHandler);
        JButton customButton = new JButton("Custom");
        customGameFields.add(widthField);
        customGameFields.add(heightField);
        customGameFields.add(numMinesField);
        customGameFields.add(customButton);

        // custom game fields holder
        JPanel customGamePanel = new JPanel(new BorderLayout());
        JLabel customGameLabel = new JLabel("", JLabel.CENTER);
        customGameLabel.setForeground(Color.RED);
        customGamePanel.add(customGameFields);
        customGamePanel.add(customGameLabel, BorderLayout.SOUTH);

        // background panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        backgroundPanel.add(titlePanel);
        backgroundPanel.add(difficultyButtonPanelHolder);
        backgroundPanel.add(customGamePanel);

        // add all to frame
        frame.add(backgroundPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        if (!buttonClickHandler.buttonPressed())
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

    public void setBoardHeight(int boardHeight)
    {
        this.boardHeight = boardHeight;
    }

    public void setBoardWidth(int boardWidth)
    {
        this.boardWidth = boardWidth;
    }

    public void setNumMines(int numMines)
    {
        this.numMines = numMines;
    }

    public synchronized void boardSet()
    {
        notifyAll();
    }
}
