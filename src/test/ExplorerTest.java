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

        //Add WALL NORTH
        this.maze.setCellType(2, 1, Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 3);

        //Add WALL SOUTH
        this.maze.setCellType(2, 3, Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 2);

        //Add WALL to EAST
        this.maze.setCellType(1, 2, Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND,LEFT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 1);

        //Add WALL to WEST
        this.maze.setCellType(3, 2, Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND,LEFT,RIGHT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 0);
    }


    @Test(timeout=10000)
    public void deadEndTest() {
        // Make robot face North
        this.robot.setHeading(IRobot.NORTH);

        // Add walls all around
        this.maze.setCellType(2, 1, Maze.WALL);//Add wall above
        this.maze.setCellType(2, 3, Maze.WALL);//Add wall below
        this.maze.setCellType(1, 2, Maze.WALL);//Add wall to left
        this.maze.setCellType(3, 2, Maze.WALL);//Add wall to right


        //Add PASSAGE NORTH
        this.maze.setCellType(2, 1, Maze.PASSAGE);
        assertTrue(
        "Failed when passage AHEAD - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.AHEAD
        );
        this.maze.setCellType(2, 1, Maze.WALL);//reset to walls all around


        //Add PASSAGE SOUTH
        this.maze.setCellType(2, 3, Maze.PASSAGE);
        assertTrue(
        "Failed when passage BEHIND - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.BEHIND
        );
        this.maze.setCellType(2, 3, Maze.WALL);//reset to walls all around


        //Add PASSAGE to WEST
        this.maze.setCellType(1, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage LEFT - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.LEFT
        );
        this.maze.setCellType(1, 2, Maze.WALL);//reset to walls all around


        //Add PASSAGE to EAST
        this.maze.setCellType(3, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage RIGHT - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.RIGHT
        );
        this.maze.setCellType(3, 2, Maze.WALL);//reset to walls all around
    }


    @Test(timeout=10000)
    public void corridorTest() {
        // Make robot face North
        this.robot.setHeading(IRobot.NORTH);

        // Add walls all around except behind
        this.maze.setCellType(2, 1, Maze.WALL);//Add wall above
        this.maze.setCellType(1, 2, Maze.WALL);//Add wall to left
        this.maze.setCellType(3, 2, Maze.WALL);//Add wall to right


        //Add PASSAGE NORTH
        this.maze.setCellType(2, 1, Maze.PASSAGE);
        assertTrue(
        "Failed when passage AHEAD - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.AHEAD
        );
        this.maze.setCellType(2, 1, Maze.WALL);//reset to walls all around


        //Add PASSAGE to WEST
        this.maze.setCellType(1, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage LEFT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.LEFT
        );
        this.maze.setCellType(1, 2, Maze.WALL);//reset to walls all around


        //Add PASSAGE to EAST
        this.maze.setCellType(3, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage RIGHT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.RIGHT
        );
        this.maze.setCellType(3, 2, Maze.WALL);//reset to walls all around
    }

    @Test(timeout=10000)
    public void junctionTest() {
        // Make robot face North
        this.robot.setHeading(IRobot.NORTH);

        // Add walls all around except behind
        this.maze.setCellType(2, 1, Maze.WALL);//Add wall above
        this.maze.setCellType(1, 2, Maze.WALL);//Add wall to left
        this.maze.setCellType(3, 2, Maze.WALL);//Add wall to right


        //Add PASSAGE NORTH
        this.maze.setCellType(2, 1, Maze.PASSAGE);
        assertTrue(
        "Failed when passage AHEAD - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.AHEAD
        );
        this.maze.setCellType(2, 1, Maze.WALL);//reset to walls all around


        //Add PASSAGE to WEST
        this.maze.setCellType(1, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage LEFT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.LEFT
        );
        this.maze.setCellType(1, 2, Maze.WALL);//reset to walls all around


        //Add PASSAGE to EAST
        this.maze.setCellType(3, 2, Maze.PASSAGE);
        assertTrue(
        "Failed when passage RIGHT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.RIGHT
        );
        this.maze.setCellType(3, 2, Maze.WALL);//reset to walls all around
    }

}
