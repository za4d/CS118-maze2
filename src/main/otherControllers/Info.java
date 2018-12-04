import java.lang.reflect.Method;
import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Scanner;
import uk.ac.warwick.dcs.maze.logic.Maze;
import uk.ac.warwick.dcs.maze.generators.PrimGenerator;
import uk.ac.warwick.dcs.maze.logic.RobotImpl;
import uk.ac.warwick.dcs.maze.gui.MazeApp;
import uk.ac.warwick.dcs.maze.logic.MazeLogic;


public class Info  implements IRobotController {
        // the robot in the maze
        private IRobot robot;
        // a flag to indicate whether we are looking for a path
        private boolean active = false;
        // a value (in ms) indicating how long we should wait
        // between moves
        private int delay;


        private static Scanner input = new Scanner(System.in);

        //HACK private static Maze maze;

        //maze dimentions
        private static int columns = 5;
        private static int rows = 5;

        // the controller used for testing
        //HACK private static Explorer controller;

        // this method is called when the "start" button is clicked
        // in the user interface
        public void start() {
                this.active = true;
                all();
                this.active = false;

        }


        public static void all() {
                // System.out.println("\n\nIRobot:\n");
                // IRobot();
                System.out.println("\n\nRobotImpl:\n");
                RobotImpl();
                System.out.println("\n\nMaze:\n");
                Maze();
                System.out.println("\n\nMazeApp:\n");
                MazeApp();
                System.out.println("\n\nMazeLogic:\n");
                MazeLogic();
                System.out.println("\n\nPrimGenerator:\n");
                PrimGenerator();
                System.out.println("\n\nconstants\n");
                constants();
                System.out.println("\n\nDone!\n");
        }

        public static void PrimGenerator() {

                Class primGenerator = new PrimGenerator().getClass();
                Method[] m = primGenerator.getMethods();
                System.out.println("There are "+m.length+" methods in the PRIMGENERATOR class...\n");
                for ( int i = 0 ; i < m.length ; i++ ) {
                        System.out.println(m[i]);
                }
        }

        public static void MazeApp() {
                MazeLogic logic = new MazeLogic();
                logic.getControllerPool().addController(new RandomController());
                Class mazeApp = new MazeApp(logic).getClass();
                Method[] m = mazeApp.getMethods();
                System.out.println("There are "+m.length+" methods in the MAZEAPP class...\n");
                for ( int i = 0 ; i < m.length ; i++ ) {
                        System.out.println(m[i]);
                }
        }

        public static void MazeLogic() {

                Class mazeLogic = new MazeLogic().getClass();
                Method[] m = mazeLogic.getMethods();
                System.out.println("There are "+m.length+" methods in the MAZELOGIC class...\n");
                for ( int i = 0 ; i < m.length ; i++ ) {
                        System.out.println(m[i]);
                }
        }

        // public static void IRobot() {
        //         private IRobot robot;
        //         Class iRobot = robot.getClass();
        //         Method[] m = iRobot.getMethods();
        //         System.out.println("There are "+m.length+" methods in the IROBOT class...\n");
        //         for ( int i = 0 ; i < m.length ; i++ ) {
        //                 System.out.println(m[i]);
        //         }
        // }

        public static void RobotImpl() {

                Class robotC = new RobotImpl().getClass();
                Method[] m = robotC.getMethods();
                System.out.println("There are "+m.length+" methods in the ROBOT class...\n");
                for ( int i = 0 ; i < m.length ; i++ ) {
                        System.out.println(m[i]);
                }
        }

        public static void Maze() {

                Class mazeC = new Maze(5,5).getClass();
                Method[] m = mazeC.getMethods();
                System.out.println("There are "+m.length+" methods in the MAZE class...\n");
                for ( int i = 0 ; i < m.length ; i++ ) {
                        System.out.println(m[i]);
                }
        }

        public static void constants() {
                System.out.print("\nHeadings:\n");
                System.out.println("IRobot.NORTH -- "+IRobot.NORTH);
                System.out.println("IRobot.EAST -- "+IRobot.EAST);
                System.out.println("IRobot.SOUTH -- "+IRobot.SOUTH);
                System.out.println("IRobot.WEST -- "+IRobot.WEST);
                System.out.print("\nDirections:\n");
                System.out.println("IRobot.AHEAD -- "+IRobot.AHEAD);
                System.out.println("IRobot.RIGHT -- "+IRobot.RIGHT);
                System.out.println("IRobot.BEHIND -- "+IRobot.BEHIND);
                System.out.println("IRobot.LEFT -- "+IRobot.LEFT);
                System.out.println("IRobot.CENTRE -- "+IRobot.CENTRE);
                System.out.print("\nTiles:\n");
                System.out.println("IRobot.WALL -- "+IRobot.WALL);
                System.out.println("IRobot.PASSAGE -- "+IRobot.PASSAGE);
                System.out.println("IRobot.BEENBEFORE -- "+IRobot.BEENBEFORE);

                // robot.advance();
                // System.out.print("\t\t START BEHIND = "+maze.getCellType(2,2));
        }


        // this method returns a description of this controller
        public String getDescription() {
                return "A controller that prints ouut class methods to terminal";
        }

        // sets the delay
        public void setDelay(int millis) {
                delay = millis;
        }

        // gets the current delay
        public int getDelay() {
                return delay;
        }

        // stops the controller
        public void reset() {
                active = false;
        }

        // sets the reference to the robot
        public void setRobot(IRobot robot) {
                this.robot = robot;
        }


}
