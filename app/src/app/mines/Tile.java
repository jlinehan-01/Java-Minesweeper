package app.mines;

import ch.aplu.jgamegrid.Actor;

public class Tile extends Actor
{
    private static final int UNOPENED_SPRITE = 0;
    private static final int EMPTY_SPRITE = 9;
    private static final int MINE_SPRITE = 10;
    private static final int HIT_SPRITE = 11;
    private static final int FLAG_SPRITE = 12;
    private int openedSpriteIndex;
    private int surroundingMines;
    private boolean opened = false;
    private boolean flagged = false;
    private boolean containsMine = false;

    public Tile()
    {
        super("unopened.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "empty.png", "mine.png", "hit.png", "flag.png");
    }

    public boolean isFlagged()
    {
        return flagged;
    }

    public boolean containsMine()
    {
        return containsMine;
    }

    public void setMine()
    {
        this.containsMine = true;
    }

    public void open(Tile[][] board, Minesweeper game)
    {
        if (!flagged && !opened)
        {
            show(openedSpriteIndex);
            opened = true;
            if (containsMine)
            {
                game.mineHit();
                return;
            }
            game.tileOpened();
            // open surrounds of empty tiles
            if (surroundingMines == 0)
            {
                openSurroundingTiles(board, game);
            }
        }
    }

    public void flag(Minesweeper game)
    {
        if (!opened)
        {
            if (flagged)
            {
                show(UNOPENED_SPRITE);
                flagged = false;
                game.removeFlag();
            }
            else
            {
                show(FLAG_SPRITE);
                flagged = true;
                game.addFlag();
            }
        }
    }

    public void clear(Tile[][] board, Minesweeper game)
    {
        if (opened && !containsMine)
        {
            if (numSurroundingFlags(board) == surroundingMines)
            {
                openSurroundingTiles(board, game);
            }
        }
    }

    public void calculateSurroundingMines(Tile[][] board)
    {
        // skip for mines
        if (containsMine)
        {
            openedSpriteIndex = HIT_SPRITE;
            return;
        }
        // calculate number of mines around tile
        int numMines = 0;
        int x = getX();
        int y = getY();
        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {
                try
                {
                    if (board[j][i].containsMine())
                    {
                        numMines++;
                    }
                }
                catch (IndexOutOfBoundsException _)
                {
                }
            }
        }
        surroundingMines = numMines;
        // set opened sprite
        if (numMines == 0)
        {
            openedSpriteIndex = EMPTY_SPRITE;
        }
        else
        {
            openedSpriteIndex = numMines;
        }
    }

    public void showMine()
    {
        if (containsMine && !opened)
        {
            show(MINE_SPRITE);
        }
    }

    private void openSurroundingTiles(Tile[][] board, Minesweeper game)
    {
        int x = getX();
        int y = getY();
        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {
                try
                {
                    board[j][i].open(board, game);
                }
                catch (IndexOutOfBoundsException _)
                {
                }
            }
        }
    }

    private int numSurroundingFlags(Tile[][] board)
    {
        int numFlags = 0;
        int x = getX();
        int y = getY();
        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {
                try
                {
                    if (board[j][i].isFlagged())
                    {
                        numFlags++;
                    }
                }
                catch (IndexOutOfBoundsException _)
                {
                }
            }
        }
        return numFlags;
    }
}
