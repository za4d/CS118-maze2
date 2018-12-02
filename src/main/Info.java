import java.lang.reflect.Method;
import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Scanner;


public class Info {
    private static Scanner input = new Scanner(System.in);

    private static Maze maze;

    //maze dimentions
    private static int columns = 5;
    private static int rows = 5;

    // the robot used for testing
    private static RobotImpl robot;

    // the controller used for testing
    private static Explorer controller;

    public static void all() {
        robot();
        System.out.println("\n\n");
        maze();
        System.out.println("\n\n");
        constants();
        System.out.println("\n\n");
    }

    public static void robot() {

        Class robotC = new RobotImpl().getClass();
        Method[] m = robotC.getMethods();
        System.out.println("There are "+m.length+" methods in the ROBOT class...\n");
        for ( int i = 0 ; i < m.length ; i++ ) {
            System.out.println(m[i]);
        }
    }

    public static void maze() {

        Class mazeC = new Maze(5,5).getClass();
        Method[] m = mazeC.getMethods();
        System.out.println("There are "+m.length+" methods in the MAZE class...\n");
        for ( int i = 0 ; i < m.length ; i++ ) {
            System.out.println(m[i]);
        }
    }

    public static void constants() {
        System.out.print("\nHeadings:");
        System.out.println("IRobot.NORTH -- "+IRobot.NORTH);
        System.out.println("IRobot.EAST -- "+IRobot.EAST);
        System.out.println("IRobot.SOUTH -- "+IRobot.SOUTH);
        System.out.println("IRobot.WEST -- "+IRobot.WEST);
        System.out.print("\nDirections");
        System.out.println("IRobot.AHEAD -- "+IRobot.AHEAD);
        System.out.println("IRobot.RIGHT -- "+IRobot.RIGHT);
        System.out.println("IRobot.BEHIND -- "+IRobot.BEHIND);
        System.out.println("IRobot.LEFT -- "+IRobot.LEFT);
        System.out.println("IRobot.CENTRE -- "+IRobot.CENTRE);
        System.out.print("\nDirections");
        System.out.println("IRobot.WALL -- "+IRobot.WALL);
        System.out.println("IRobot.PASSAGE -- "+IRobot.PASSAGE);
        System.out.println("IRobot.BEENBEFORE -- "+IRobot.BEENBEFORE);

        robot.advance();
        System.out.print("\t\t START BEHIND = "+maze.getCellType(2,2));
    }



    public void setupMaze() {
        // generate a maze with the test dimensions
        this.maze = new Maze(this.columns, this.rows);

        // fill the maze with passages
        for (int x=0; x<this.columns; x++) {
            for (int y=0; y<this.rows; y++) {
                this.maze.setCellType(x, y, Maze.PASSAGE);
            }
        }

        // set the starting point somewhere near the middle
        this.maze.setStart(2,2);
        this.maze.setFinish(0,0);

        // initialise the robot
        this.robot = new RobotImpl();
        this.robot.setMaze(this.maze);

        // initialise the random robot controller
        this.controller = new Explorer();
        this.controller.setRobot(this.robot);

        // Make robot face North
        this.robot.setHeading(IRobot.NORTH);
    }

}
