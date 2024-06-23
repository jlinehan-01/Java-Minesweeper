package app;

public class Main
{
    public static void main(String[] args)
    {
        Thread thread = new Thread(new Driver());
        thread.start();
    }
}
