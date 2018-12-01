import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import uk.ac.warwick.dcs.maze.logic.Maze;
import uk.ac.warwick.dcs.maze.generators.PrimGenerator;
import uk.ac.warwick.dcs.maze.logic.IRobot;
import uk.ac.warwick.dcs.maze.logic.RobotImpl;
import java.awt.Point;

/*
    This class contains unit tests for the HomingController class.
*/
public class ExplorerTest {
    // the dimensions of the test maze
    private int columns = 5;
    private int rows = 5;

    // the maze used for testing
    private Maze maze;

    // the robot used for testing
    private RobotImpl robot;

    // the controller used for testing
    private Explorer controller;

    /*
        This method is run before all tests.
    */
    @Before
    public void setupTests() {
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
    }

    @Test(timeout=10000)
    public void nonwallExitsTest() {
        //Add wall above
        this.maze.setCellType(2, 1, Maze.WALL);
        assertTrue(
        "Failed when Wall added ABOVE - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 3);

        //Add wall below
        this.maze.setCellType(2, 3, Maze.WALL);
        assertTrue(
        "Failed when Wall added ABOVE,BELOW - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 2);

        //Add wall to left
        this.maze.setCellType(1, 2, Maze.WALL);
        assertTrue(
        "Failed when Wall added ABOVE,BELOW,LEFT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 1);

        //Add wall to right
        this.maze.setCellType(3, 2, Maze.WALL);
        assertTrue(
        "Failed when Wall added ABOVE,BELOW,LEFT,RIGHT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 0);
    }

}
