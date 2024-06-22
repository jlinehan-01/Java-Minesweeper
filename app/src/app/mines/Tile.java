package app.mines;

import ch.aplu.jgamegrid.Actor;

public class Tile extends Actor
{
    private static final int UNOPENED_SPRITE = 0;
    private static final int EMPTY_SPRITE = 9;
    private static final int MINE_SPRITE = 10;
    private static final int HIT_SPRITE = 11;
    private static final int FLAG_SPRITE = 12;
    private static final int EMPTY = 0;

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

    public boolean open()
    {
        if (!flagged && !opened)
        {
            show(openedSpriteIndex);
            opened = true;
            return true;
        }
        return false;
    }

    public boolean flag()
    {
        if (flagged)
        {
            show(UNOPENED_SPRITE);
            flagged = false;
            return false;
        }
        else
        {
            show(FLAG_SPRITE);
            flagged = true;
            return true;
        }
    }

    public void showMine()
    {
        if (containsMine && !opened)
        {
            show(MINE_SPRITE);
        }
    }

    public boolean isOpened()
    {
        return opened;
    }

    public boolean isEmpty()
    {
        return surroundingMines == EMPTY;
    }

    public int getSurroundingMines()
    {
        return surroundingMines;
    }

    public void setSurroundingMines(int numSurroundingMines)
    {
        this.surroundingMines = numSurroundingMines;
        if (containsMine)
        {
            openedSpriteIndex = HIT_SPRITE;
        }
        else if (isEmpty())
        {
            openedSpriteIndex = EMPTY_SPRITE;
        }
        else
        {
            openedSpriteIndex = numSurroundingMines;
        }
    }
}
