package app.mines;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGSound;

/**
 * Represents a tile on the game board which may contain a mine
 *
 * @author Joshua Linehan
 */
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

    /**
     * Creates an Actor with the necessary sprites for a Tile
     */
    public Tile()
    {
        super("unopened.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "empty.png", "mine.png", "hit.png", "flag.png");
    }

    /**
     * Returns whether this tile is currently flagged
     *
     * @return true if this Tile is flagged, false otherwise
     */
    public boolean isFlagged()
    {
        return flagged;
    }

    /**
     * Returns whether this tile contains a mine
     *
     * @return true if this tile contains a mine, false otherwise
     */
    public boolean containsMine()
    {
        return containsMine;
    }

    /**
     * Designates this Tile as a mine location
     */
    public void setMine()
    {
        this.containsMine = true;
    }

    /**
     * Opens this Tile if possible
     *
     * @return true if this Tile was opened, false if it was not
     */
    public boolean open()
    {
        if (!flagged && !opened)
        {
            if (containsMine)
            {
                gameGrid.playSound(GGSound.EXPLODE);
            }
            else
            {
                gameGrid.playSound(GGSound.CLICK);
            }
            show(openedSpriteIndex);
            opened = true;
            return true;
        }
        return false;
    }

    /**
     * Adds or removes a flag from this tile. Must only be called on unopened Tiles
     *
     * @return true if a flag was added to the tile, false if a flag was removed from the tile
     */
    public boolean flag()
    {
        if (flagged)
        {
            gameGrid.playSound(GGSound.BOING);
            show(UNOPENED_SPRITE);
            flagged = false;
            return false;
        }
        else
        {
            gameGrid.playSound(GGSound.PING);
            show(FLAG_SPRITE);
            flagged = true;
            return true;
        }
    }

    /**
     * Displays a mine if this Tile is unopened and contains a mine. Called when the game is lost
     */
    public void showMine()
    {
        if (containsMine && !opened)
        {
            show(MINE_SPRITE);
        }
    }

    /**
     * Returns if this Tile has been opened
     *
     * @return true if this Tile has been opened, false if it has not
     */
    public boolean isOpened()
    {
        return opened;
    }

    /**
     * Returns whether this tile empty, meaning there are no mines in the Tiles that surround it
     *
     * @return true is this tile is empty, false if it is not
     */
    public boolean isEmpty()
    {
        return surroundingMines == EMPTY;
    }

    public int getSurroundingMines()
    {
        return surroundingMines;
    }

    /**
     * Sets this Tile's surroundingMines and chooses the appropriate sprite to display once opened
     *
     * @param numSurroundingMines The number of mines in the Tiles that surround this Tile
     */
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
