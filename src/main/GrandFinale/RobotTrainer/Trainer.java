import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.warwick.dcs.maze.logic.Maze;
import uk.ac.warwick.dcs.maze.generators.PrimGenerator;
import uk.ac.warwick.dcs.maze.logic.RobotImpl;
import uk.ac.warwick.dcs.maze.gui.MazeApp;
import uk.ac.warwick.dcs.maze.logic.MazeLogic;
import java.nio.file.Files;


/*  Setup
1.  Initialise
Create populations of N agents
Draw
2.  Selections
Evaluate the fitness of these agents and make a mating exploreControl
3.  Reproduction
For each agent:
a. pick parents accoring to fitness
b. crossover - create the `child` by combining parents
c. mutation - mutate the childs DNA probabalisticly
d. add the child to the next population
4.  Repeat for new population

*/
public class Trainer {
  /*
  Tests whether the random controller causes the robot
  to walk into walls.
  */
  public void doesNotRunIntoWallsTest() {
    // generate a random maze
    Maze maze = (new PrimGenerator()).generateMaze();

    // initialise the robot
    RobotImpl robot = new RobotImpl();
    robot.setMaze(maze);

    // initialise the random robot controller
    RandomController controller = new RandomController();
    controller.setRobot(robot);

    // run the controller
    controller.start();

    // test whether the robot walked into walls during this run
    assertTrue(
    "RandomController walks into walls!",
    robot.getCollisions() == 0);
  }

  public void write(){
    String text = "Text to save to file";
    Files.write(Paths.get("./fileName.txt"), text.getBytes());
  }
}
