package app;

import app.mines.Minesweeper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;

public class ScoreHandler
{
    public static final int NO_BEST = -1;
    private static final String FILENAME = "bestScores.json";

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
