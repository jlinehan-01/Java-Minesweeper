package app;

/**
 * Handles creation of the initial Driver instance
 *
 * @author Joshua Linehan
 */
public class Main
{
    /**
     * The entry point of the program, creates a new Driver thread and runs it
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args)
    {
        Thread thread = new Thread(new Driver());
        thread.start();
    }
}
