package app.gui;

import javax.swing.*;
import java.awt.*;

/**
 * The GUI used to configure the game
 *
 * @author Joshua Linehan
 */
public class Menu
{
    /**
     * The text displayed on the Easy difficulty button
     */
    public static final String EASY_BUTTON_TEXT = "Easy";
    /**
     * The text displayed on the Medium difficulty button
     */
    public static final String MEDIUM_BUTTON_TEXT = "Medium";
    /**
     * The text displayed on the Hard difficulty button
     */
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

    /**
     * Creates and displays the GUI
     *
     * @param title The title displayed on the GUI
     */
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

    /**
     * Gets the number of mines to be placed on the board once entered by the user
     *
     * @return The number of mines
     */
    public int getNumMines()
    {
        waitForButtonPress();
        return numMines;
    }

    /**
     * Sets the number of mines on the board
     *
     * @param numMines the number of mines, entered by the user
     */
    public void setNumMines(int numMines)
    {
        this.numMines = numMines;
    }

    /**
     * Gets the height of the board once entered by the user
     *
     * @return The number of tiles high the board is to be
     */
    public int getBoardHeight()
    {
        waitForButtonPress();
        return boardHeight;
    }

    /**
     * Sets the tile height of the board
     *
     * @param boardHeight The height of the board, entered by the user
     */
    public void setBoardHeight(int boardHeight)
    {
        this.boardHeight = boardHeight;
    }

    /**
     * Gets the width of the board once entered by the user
     *
     * @return The number of tiles wide the board is to be
     */
    public int getBoardWidth()
    {
        waitForButtonPress();
        return boardWidth;
    }

    /**
     * Sets the tile width of the board
     *
     * @param boardWidth The width of the board, entered by the user
     */
    public void setBoardWidth(int boardWidth)
    {
        this.boardWidth = boardWidth;
    }

    /**
     * Closes the menu
     */
    public void close()
    {
        frame.dispose();
    }

    /**
     * Flags that board data has been received and wakes any waiting threads
     */
    public synchronized void boardSet()
    {
        buttonPressed = true;
        notifyAll();
    }

    /**
     * Displays text in response to an invalid custom input
     *
     * @param errorMessage The text to be displayed
     */
    public void setErrorText(String errorMessage)
    {
        errorText.setText(errorMessage);
    }

    /**
     * Pauses execution until board information is set
     */
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
