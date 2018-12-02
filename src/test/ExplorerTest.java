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

        // Make robot face North
        this.robot.setHeading(IRobot.NORTH);
    }

    @Test(timeout=10000)
    public void nonwallExitsTest() {

        //Add WALL NORTH
        setNorth(Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 3);

        //Add WALL SOUTH
        setSouth(Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 2);

        //Add WALL to WEST
        setWest(Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND,LEFT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 1);

        //Add WALL to EAST
        setEast(Maze.WALL);
        assertTrue(
        "Failed when Wall added AHEAD,BEHIND,LEFT,RIGHT - Exits = "+this.controller.nonwallExits(),
        this.controller.nonwallExits() == 0);
    }


    @Test(timeout=10000)
    public void deadEndTest() {

        // Add walls all around
        setNorth(Maze.WALL);//Add wall above
        setSouth(Maze.WALL);//Add wall below
        setWest(Maze.WALL);//Add wall to left
        setEast(Maze.WALL);//Add wall to right


        //Add PASSAGE NORTH
        setNorth(Maze.PASSAGE);
        assertTrue(
        "Failed when passage AHEAD - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.AHEAD
        );
        setNorth(Maze.WALL);//reset to walls all around


        //Add PASSAGE SOUTH
        setSouth(Maze.PASSAGE);
        assertTrue(
        "Failed when passage BEHIND - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.BEHIND
        );
        setSouth(Maze.WALL);//reset to walls all around


        //Add PASSAGE to WEST
        setWest(Maze.PASSAGE);
        assertTrue(
        "Failed when passage LEFT - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.LEFT
        );
        setWest(Maze.WALL);//reset to walls all around


        //Add PASSAGE to EAST
        setEast(Maze.PASSAGE);
        assertTrue(
        "Failed when passage RIGHT - "+this.controller.deadEnd(),
        this.controller.deadEnd() == IRobot.RIGHT
        );
        setEast(Maze.WALL);//reset to walls all around
    }


    @Test(timeout=10000)
    public void corridorTest() {

        // Add walls all around except behind
        setNorth(Maze.WALL);//Add wall above
        setWest(Maze.WALL);//Add wall to left
        setEast(Maze.WALL);//Add wall to right


        //Add PASSAGE NORTH
        setNorth(Maze.PASSAGE);
        assertTrue(
        "Failed when passage AHEAD - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.AHEAD
        );
        setNorth(Maze.WALL);//reset to walls all around


        //Add PASSAGE to WEST
        setWest(Maze.PASSAGE);
        assertTrue(
        "Failed when passage LEFT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.LEFT
        );
        setWest(Maze.WALL);//reset to walls all around


        //Add PASSAGE to EAST
        setEast(Maze.PASSAGE);
        assertTrue(
        "Failed when passage RIGHT - "+this.controller.corridor(),
        this.controller.corridor() == IRobot.RIGHT
        );
        setEast(Maze.WALL);//reset to walls all around
    }



    //JUNCTION and CROSSROADS tested with custom Maze
/*
    @Test(timeout=15000)
    public void junctionTest() {

        // Add walls all around except behind
        setNorth(Maze.WALL);//Add wall above
        setbeenbefore(IRobot.SOUTH);//Add wall below
        setbeenbefore(IRobot.WEST);//Add wall to left
        setbeenbefore(IRobot.EAST);//Add wall to right


        // SINGLE UNEXPLORED

        //PASSAGE to WEST
        setWest(Maze.PASSAGE);
        assertTrue(
        "Failed when 1 Unexplored passage LEFT - "+this.controller.junction(),
        this.controller.junction() == IRobot.LEFT
        );
        setbeenbefore(IRobot.WEST);//reset to walls all around


        //PASSAGE to EAST
        setEast(Maze.PASSAGE);
        assertTrue(
        "Failed when 1 Unexplored passage RIGHT - "+this.controller.junction(),
        this.controller.junction() == IRobot.RIGHT
        );
        setbeenbefore(IRobot.EAST);//reset to walls all around

        //PASSAGE to SOUTH
        setEast(Maze.PASSAGE);
        assertTrue(
        "Failed when 1 Unexplored passage BEHIND - "+this.controller.junction(),
        this.controller.junction() == IRobot.BEHIND
        );
        setbeenbefore(IRobot.SOUTH);//reset to BEENBEFORE

        // ALL EXPLORED

        //PASSAGE to WEST
        int dir = 0;
        while (dir != IRobot.LEFT) {
            dir = this.controller.junction();
            assertFalse(
            "Failed when All Unexplored, chose passage BEHIND ",
            dir == IRobot.BEHIND
            );
        }
        //PASSAGE to EAST
        while (dir != IRobot.RIGHT) {
            dir = this.controller.junction();
            assertFalse(
            "Failed when All Unexplored, chose passage BEHIND ",
            this.controller.junction() == IRobot.BEHIND
            );
        }
    }
    */

    //Been beenbeforeExits tesed by obsevations
    //System.out.println(beenbeforeExits());

    private void setNorth(int wallType) {
        this.maze.setCellType(2, 1, wallType);
    }
    private void setSouth(int wallType) {
        this.maze.setCellType(2, 3, wallType);
    }
    private void setEast(int wallType) {
        this.maze.setCellType(3, 2, wallType);
    }
    private void setWest(int wallType) {
        this.maze.setCellType(1, 2, wallType);
    }



    // private void setbeenbefore(int dir) {
    //     this.robot.setHeading(dir);
    //     robot.advance();
    //     this.robot.face(IRobot.BEHIND);
    //     robot.advance();
    //     // Make robot face North
    //     this.robot.setHeading(IRobot.NORTH);
    // }
}
