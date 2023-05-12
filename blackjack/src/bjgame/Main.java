package bjgame;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main
{
    public static void main(String argv[])
    {
        Logger logger = Logger.getLogger("bjgame");
        logger.setLevel(Level.WARNING);

        for (String arg : argv) {
            switch (arg) {
                case "-v", "--verbose":
                    logger.setLevel(Level.INFO);
                    break;
                case "-vv", "--debug":
                    logger.setLevel(Level.FINE);
                    break;
                case "-h", "--help", "-?":
                    help();
                    System.exit(1);
                default:
                    System.err.println("invalid option: " + arg);
                    usage();
                    System.exit(1);
            }
        }

        GameFrame game = new GameFrame();
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                game.initialize();
                game.startGame();
                game.display();
            }
        });
    }

    private static void help()
    {
        usage();
    }

    private static void usage()
    {
        System.err.println("usage: java bjgame.Main [-v | --verbose]");
    }
}
