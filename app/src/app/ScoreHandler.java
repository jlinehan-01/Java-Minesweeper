package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ScoreHandler
{
    public static final int NO_BEST = -1;
    private static final String FILENAME = "bestScores.json";
    private static final String EMPTY_STR = "";

    public int getBest(int width, int height, int numMines)
    {
        File bestScores = new File(FILENAME);
        try
        {
            bestScores.createNewFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            Scanner reader = new Scanner(bestScores);
            StringBuilder data = new StringBuilder(EMPTY_STR);
            while (reader.hasNext())
            {
                data.append(reader.nextLine());
            }
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data.toString());
            jsonObject = (JSONObject) jsonObject.get(width);
            jsonObject = (JSONObject) jsonObject.get(height);
            return (int) jsonObject.get(numMines);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (ParseException e)
        {
            return NO_BEST;
        }
    }
}
