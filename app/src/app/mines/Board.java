package app.mines;

import ch.aplu.jgamegrid.Location;

import java.util.Random;

/**
 * Contains the Tiles of a Minesweeper game
 *
 * @author Joshua Linehan
 */
public class Board
{
    private final int width;
    private final int height;
    private final int numMines;
    private final Tile[][] tiles;
    private final int target;
    private final Minesweeper game;
    private final Random random = new Random();

    private int tilesOpened = 0;
    private int numFlags = 0;

    /**
     * Creates a new Board with the given specifications
     *
     * @param game     The game the Board belongs to
     * @param width    The width in tiles of the Board
     * @param height   The height in tiles of the Board
     * @param numMines The number of mines on the Board
     */
    public Board(Minesweeper game, int width, int height, int numMines)
    {
        this.game = game;
        this.height = height;
        this.width = width;
        this.numMines = numMines;
        assert (numMines < (width * height));
        target = (height * width) - numMines;
        tiles = new Tile[height][width];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                Tile tile = new Tile();
                tiles[y][x] = tile;
                game.addActor(tile, new Location(x, y));
            }
        }
    }

    /**
     * Place numMines mines on the board after the first tile is opened
     *
     * @param firstClick The Location of the first tile opened
     */
    public void setMines(Location firstClick)
    {
        // place mines
        for (int i = 0; i < numMines; )
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (tiles[y][x].containsMine() || (x == firstClick.getX() && y == firstClick.getY()))
            {
                continue;
            }
            tiles[y][x].setMine();
            i++;
        }
        // calculate numbers
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                tiles[y][x].setSurroundingMines(calculateSurroundingMines(x, y));
            }
        }
        open(firstClick);
    }

    /**
     * Displays the location of each mine after one is hit
     */
    public void showMines()
    {
        for (Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                tile.showMine();
            }
        }
    }

    /**
     * Finds if the board is solved
     *
     * @return true if the Board has been solved, false otherwise
     */
    public boolean isComplete()
    {
        return tilesOpened == target;
    }

    /**
     * Displays a flag on each unflagged mine once the game is won
     */
    public void setFlags()
    {
        for (Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                if (tile.containsMine() && !tile.isFlagged())
                {
                    flag(tile.getLocation());
                }
            }
        }
    }

    /**
     * Attempts to open the Tile at location
     *
     * @param location the location of the Tile to be opened
     */
    public void open(Location location)
    {
        Tile tile = tiles[location.getY()][location.getX()];
        if (tile.open())
        {
            if (tile.containsMine())
            {
                game.mineHit();
            }
            else
            {
                tilesOpened++;
                if (tile.isEmpty())
                {
                    clear(location);
                }
            }
        }
    }

    /**
     * Attempts to flag or unflag the Tile at location
     *
     * @param location the location of the Tile to be flagged or flagged
     */
    public void flag(Location location)
    {
        Tile tile = tiles[location.getY()][location.getX()];
        if (!tile.isOpened())
        {
            if (tile.flag())
            {
                numFlags++;
            }
            else
            {
                numFlags--;
            }
        }
    }

    /**
     * Opens the Tiles around a Location
     *
     * @param location the centre of the area to be cleared
     */
    public void clear(Location location)
    {
        int x = location.getX();
        int y = location.getY();
        if (tiles[y][x].isOpened() && (tiles[y][x].getSurroundingMines() == getSurroundingFlags(location)))
        {
            for (int i = y - 1; i <= y + 1; i++)
            {
                for (int j = x - 1; j <= x + 1; j++)
                {
                    try
                    {
                        open(new Location(j, i));
                    }
                    catch (ArrayIndexOutOfBoundsException ignored)
                    {
                    }
                }
            }
        }
    }

    /**
     * Finds the number of tiles that have been flagged
     *
     * @return the number of tiles current flagged
     */
    public int getNumFlags()
    {
        return numFlags;
    }

    /**
     * Calculates the number of mines in the Tiles surrounding a location
     *
     * @param x The x coordinate of the Tile
     * @param y The y coordinate of the Tile
     * @return The number of mines in the Tiles surrounding (x, y)
     */
    private int calculateSurroundingMines(int x, int y)
    {
        int surroundingMines = 0;
        for (int i = y - 1; i <= y + 1; i++)
        {
            for (int j = x - 1; j <= x + 1; j++)
            {
                try
                {
                    if (tiles[i][j].containsMine())
                    {
                        surroundingMines++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException ignored)
                {
                }
            }
        }
        return surroundingMines;
    }

    /**
     * Calculates the number of flags surrounding a location
     *
     * @param location The centre Tile of the area to be checked
     * @return The number of flags in the area surrounding location
     */
    private int getSurroundingFlags(Location location)
    {
        int surroundingFlags = 0;
        int x = location.getX();
        int y = location.getY();

        for (int i = y - 1; i <= y + 1; i++)
        {
            for (int j = x - 1; j <= x + 1; j++)
            {
                try
                {
                    if (tiles[i][j].isFlagged())
                    {
                        surroundingFlags++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException ignored)
                {
                }
            }
        }

        return surroundingFlags;
    }
}
