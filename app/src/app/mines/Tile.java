package app.mines;

import ch.aplu.jgamegrid.Actor;

public class Tile extends Actor
{
    private static final int UNOPENED_SPRITE = 0;
    private static final int FLAG_SPRITE = 4;
    private int openedSpriteIndex;
    private boolean opened = false;
    private boolean flagged = false;
    private int surroundingMines;

    public boolean containsMine()
    {
        return containsMine;
    }

    public void setMine()
    {
        this.containsMine = true;
    }

    private boolean containsMine = false;
    public Tile()
    {
        super("unopened.png", "empty.png", "mine.png", "hit.png", "flag.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png");
    }
    void open(){}
    void flag()
    {
        if (!opened)
        {
            if (flagged)
            {
                show(UNOPENED_SPRITE);
                flagged = false;
            }
            else
            {
                show(FLAG_SPRITE);
                flagged = true;
            }
        }
    }
    void clear(){}
    void calculateSurroundingMines(Tile[][] board){}
}
