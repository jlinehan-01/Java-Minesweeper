package app;

import app.mines.Minesweeper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;

/**
 * Handles storage and retrieval of best scores
 *
 * @author Joshua Linehan
 */
public class ScoreHandler
{
    /**
     * An integer representing that no best score for the given Board configuration was found
     */
    public static final int NO_BEST = -1;

    private static final String FILENAME = "bestScores.json";

    /**
     * Finds the current best score for the given board configuration
     *
     * @param width    The width of the board to find the score for
     * @param height   The height of the board to find the score for
     * @param numMines The number of mines of the board to find the score for
     * @return The current best score of the board if one is found, or
     * {@link #NO_BEST an integer indicating that no best score was found}
     */
    public int getBest(int width, int height, int numMines)
    {
        JsonObject bestScoresJson = getJson();

        // query json for best score
        try
        {
            return bestScoresJson.getAsJsonObject(String.valueOf(width)).getAsJsonObject(String.valueOf(height)).get(String.valueOf(numMines)).getAsInt();
        }
        catch (NullPointerException e)
        {
            return NO_BEST;
        }
    }

    /**
     * Stores a result as the new best score if it is better than the previous best
     *
     * @param width    the width of the game board
     * @param height   the height of the game board
     * @param numMines the number of miens on the game board
     * @param score    the time taken to complete the game
     */
    public void handleResult(int width, int height, int numMines, int score)
    {
        String widthKey = String.valueOf(width);
        String heightKey = String.valueOf(height);
        String minesKey = String.valueOf(numMines);
        int previousBest = getBest(width, height, numMines);
        if ((previousBest == NO_BEST || score < previousBest) && score != Minesweeper.LOSS)
        {
            JsonObject bestScoresJson = getJson();
            if (bestScoresJson == null)
            {
                bestScoresJson = new JsonObject();
            }
            JsonElement newBest = new JsonPrimitive(score);

            // create new height json object
            JsonObject heightObject;
            try
            {
                heightObject = bestScoresJson.getAsJsonObject(widthKey).getAsJsonObject(heightKey);
            }
            catch (NullPointerException e)
            {
                heightObject = new JsonObject();
            }
            if (heightObject == null)
            {
                heightObject = new JsonObject();
            }
            heightObject.add(minesKey, newBest);

            // create new width json object
            JsonObject widthObject;
            try
            {
                widthObject = bestScoresJson.getAsJsonObject(widthKey);
            }
            catch (NullPointerException e)
            {
                widthObject = new JsonObject();
            }
            if (widthObject == null)
            {
                widthObject = new JsonObject();
            }
            widthObject.add(heightKey, heightObject);

            // add new data to json
            bestScoresJson.add(widthKey, widthObject);
            writeJson(bestScoresJson);
        }
    }

    /**
     * Parses a JSON file into a JsonObject, creating the file if it does not exist
     *
     * @return a JsonObject representing the contents of the file
     */
    private JsonObject getJson()
    {
        // ensure best scores file exists
        File scoresFile = new File(FILENAME);
        try
        {
            scoresFile.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        // read file contents into json
        BufferedReader bufferedReader;
        try
        {
            bufferedReader = new BufferedReader(new FileReader(scoresFile));
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        return gson.fromJson(bufferedReader, JsonObject.class);
    }

    /**
     * Writes a JsonObject to a JSON file
     *
     * @param jsonObject The object to be written to the file
     */
    private void writeJson(JsonObject jsonObject)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME));
            writer.write(jsonObject.toString());
            writer.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
