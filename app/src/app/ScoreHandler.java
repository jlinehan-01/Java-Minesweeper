package app;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ScoreHandler
{
    public static final int NO_BEST = -1;
    private static final String FILENAME = "bestScores.json";

    public int getBest(int width, int height, int numMines)
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
        JsonObject bestScoresJson = gson.fromJson(bufferedReader, JsonObject.class);

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
}
