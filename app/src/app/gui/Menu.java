package app.gui;

import javax.swing.*;
import java.awt.*;

public class Menu
{
    public static final String EASY_BUTTON_TEXT = "Easy";
    public static final String MEDIUM_BUTTON_TEXT = "Medium";
    public static final String HARD_BUTTON_TEXT = "Hard";

    private static final int HEIGHT = 310;
    private static final int WIDTH = 287;
    private static final int FIELD_NUM_COLUMNS = 4;
    private static final String WIDTH_FIELD_DEFAULT = "Width";
    private static final String HEIGHT_FIELD_DEFAULT = "Height";
    private static final String MINES_FIELD_DEFAULT = "Mines";

    private final JFrame frame;
    private final JLabel errorText;

    private int boardHeight;
    private int boardWidth;
    private int numMines;
    private boolean buttonPressed = false;

    public Menu(String title)
    {
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

        JButton easyButton = new JButton(EASY_BUTTON_TEXT);
        JButton mediumButton = new JButton(MEDIUM_BUTTON_TEXT);
        JButton hardButton = new JButton(HARD_BUTTON_TEXT);

        difficultyButtonPanel.add(easyButton);
        difficultyButtonPanel.add(mediumButton);
        difficultyButtonPanel.add(hardButton);

        DifficultyButtonHandler difficultyButtonHandler = new DifficultyButtonHandler(this);
        easyButton.addActionListener(difficultyButtonHandler);
        mediumButton.addActionListener(difficultyButtonHandler);
        hardButton.addActionListener(difficultyButtonHandler);

        // difficulty button holder
        JPanel difficultyButtonPanelHolder = new JPanel(new GridLayout(3, 1));
        difficultyButtonPanelHolder.add(new JPanel());
        difficultyButtonPanelHolder.add(difficultyButtonPanel, BorderLayout.CENTER);
        difficultyButtonPanelHolder.add(new JPanel());

        // custom game fields
        JPanel customGameFields = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JTextField widthField = new JTextField(WIDTH_FIELD_DEFAULT, FIELD_NUM_COLUMNS);
        JTextField heightField = new JTextField(HEIGHT_FIELD_DEFAULT, FIELD_NUM_COLUMNS);
        JTextField numMinesField = new JTextField(MINES_FIELD_DEFAULT, FIELD_NUM_COLUMNS);

        widthField.addFocusListener(focusEventHandler);
        heightField.addFocusListener(focusEventHandler);
        numMinesField.addFocusListener(focusEventHandler);

        JButton customButton = new JButton("Custom");
        CustomButtonHandler customButtonHandler = new CustomButtonHandler(widthField, heightField, numMinesField, this);
        customButton.addActionListener(customButtonHandler);

        customGameFields.add(widthField);
        customGameFields.add(heightField);
        customGameFields.add(numMinesField);
        customGameFields.add(customButton);

        // custom game fields holder
        JPanel customGamePanel = new JPanel(new BorderLayout());
        errorText = new JLabel("", JLabel.CENTER);
        errorText.setForeground(Color.RED);
        customGamePanel.add(customGameFields);
        customGamePanel.add(errorText, BorderLayout.SOUTH);

        // background panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        backgroundPanel.add(titlePanel);
        backgroundPanel.add(difficultyButtonPanelHolder);
        backgroundPanel.add(customGamePanel);

        // add all to frame
        frame.add(backgroundPanel);
        frame.setContentPane(backgroundPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public int getNumMines()
    {
        waitForButtonPress();
        return numMines;
    }

    public void setNumMines(int numMines)
    {
        this.numMines = numMines;
    }

    public int getBoardHeight()
    {
        waitForButtonPress();
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight)
    {
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth()
    {
        waitForButtonPress();
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth)
    {
        this.boardWidth = boardWidth;
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

    public synchronized void boardSet()
    {
        buttonPressed = true;
        notifyAll();
    }

    public void setErrorText(String errorMessage)
    {
        errorText.setText(errorMessage);
    }
}
