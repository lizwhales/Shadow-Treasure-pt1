import bagel.*;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;


/**
 * An example Bagel game.
 */
public class ShadowTreasure extends AbstractGame {

    // for rounding double number; use this to print the location of the player
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void printInfo(double x, double y, int e){
        System.out.println(df.format(x) + "," + df.format(y) + "," + e);
    }

    // setting up constant variables
    public static final double STEP_SIZE = 10;
    private static final double SCORE_DISTANCE = 50;
    private static final Point TEXT_POINT = new Point(20, 760);


    private final Font font = new Font("res/font/DejaVuSans-Bold.ttf", 20);

    // if the sandwich is there or not
    private boolean sandwichExistence = true;

    //  setting up variables

    private final Image player;
    private final Image sandwich;
    private final Image zombie;
    private final Image background;
    private int e;
    private double x;
    private double y;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public double xd = 1;
    public double yd = 1;

    private int ticks = 0;



    // create the avatar class to get positional values

    Player avatar = new Player(x,y,e);


    public ShadowTreasure() throws IOException {
        // Add code to initialize other attributes as needed


        // importing images

        player = new Image("res/images/player.png");
        sandwich = new Image("res/images/sandwich.png");
        zombie = new Image ("res/images/zombie.png");
        background = new Image("res/images/background.png");

        this.loadEnvironment("res/IO/environment.csv");

    }

    /**
     * Load from input file
     */


    private void loadEnvironment(String filename) {
        // Code here to read from the file and set up the environment
        String FILENAME = "res/IO/environment.csv";
        String line;
        String splitBy = ",";
        int i = 1;
        try {
        // parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));

            while ((line = br.readLine()) != null)
            {
                String[] vars = line.split(splitBy);    // use comma as separator
                if(i == 1) {
                    x = Integer.parseInt(vars[1]);
                    y = Integer.parseInt(vars[2]);
                    e = Integer.parseInt(vars[3]);
                }else if (i == 2){
                    x1 = Integer.parseInt(vars[1]);
                    y1 = Integer.parseInt(vars[2]);
                }else if (i==3){
                    x2 = Integer.parseInt(vars[1]);
                    y2 = Integer.parseInt(vars[2]);
                }
                i++;
            }

            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    // sets positions to avatar

    avatar.setX(x);
    avatar.setY(y);
    avatar.setE(e);


    }



    /**
     * Performs a state update.
     */
    @Override
    public void update(Input input) {

        // Logic to update the game, as per specification must go here

        background.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        Point playerStart = new Point(x, y);
        Point zombieStart = new Point(x1, y1);
        Point sandwichStart = new Point(x2, y2);

        // draws sandwich and sets it as true

        if (sandwichExistence) {
            sandwich.draw(sandwichStart.x, sandwichStart.y);
        }


        // sets tick count and counts

        ticks += 1;
        if(ticks==10) {

            ticks=0;
            double speed = STEP_SIZE;


            if (new Point(x, y).distanceTo(zombieStart) < SCORE_DISTANCE) {
                e -= 3;
                avatar.setE(e);
                Window.close();
            } else if (new Point(x, y).distanceTo(sandwichStart) < SCORE_DISTANCE && sandwichExistence) {
                e += 5;
                avatar.setE(e);
                sandwichExistence = false;

            }

            // shows player pos info on sout

            printInfo(x, y, avatar.getE());


            // player movements due to energy algorithm

            if (e >= 3) {
                xd = (x1 - x) / (new Point(x, y).distanceTo(zombieStart));
                yd = (y1 - y) / (new Point(x, y).distanceTo(zombieStart));
                x += speed * xd;
                y += speed * yd;

            } else {

                xd = (x2 - x) / (new Point(x, y).distanceTo(sandwichStart));
                yd = (y2 - y) / (new Point(x, y).distanceTo(zombieStart));
                x += speed * xd;
                y += speed * yd;
            }



        }

        // draws images and font

        player.draw(playerStart.x, playerStart.y);
        zombie.draw(zombieStart.x, zombieStart.y);
        font.drawString("Energy: " + e, TEXT_POINT.x, TEXT_POINT.y);




    }



    /**
     * The entry point for the program.
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }
}
