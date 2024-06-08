package app;

import app.mines.Minesweeper;

public class Driver
{
    private static final int DEFAULT_WIDTH = 30;
    private static final int DEFAULT_HEIGHT = 16;
    private static final int DEFAULT_NUM_MINES = 99;

    public static void main(String[] args)
    {
        Minesweeper game = new Minesweeper(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_NUM_MINES);
        int result = game.runGame();
        System.out.println(result);
    }
}
