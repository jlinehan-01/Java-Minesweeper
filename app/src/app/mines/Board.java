package app.mines;

import ch.aplu.jgamegrid.Location;

import java.util.Random;

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

    public boolean isComplete()
    {
        return tilesOpened == target;
    }

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

    public int getNumFlags()
    {
        return numFlags;
    }

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
