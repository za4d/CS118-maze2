import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

public class RandomController implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;

    // this method is called when the "start" button is clicked
    // in the user interface
    public void start() {
        this.active = true;

        // loop while we haven't found the exit and the agent
        // has not been interrupted
        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
            //TASK 1.2.2 ~ Generate a random number between 0-3 (inclusive)
            int rand = (int)Math.floor(Math.random() * 4);

            // turn into one of the four directions, as determined
            // by the random number that was generated:
            // 0: forward- 25% chance
            // 1: right  - 25% chance
            // 2: behind - 25% chance
            // 3: left   - 25% chance
            switch (rand) {
            case 0:
                robot.face(IRobot.AHEAD);
                break;
            case 1:
                robot.face(IRobot.RIGHT);
                break;
            case 2:
                robot.face(IRobot.BEHIND);
                break;
            case 3:
                robot.face(IRobot.LEFT);
                break;
            }

            //When changeDirection is true it will force the robot to change direction
            boolean changeDirection = false;

            //TASK 1.1.1 ~ When wall ahead robot doesnt advance and changes direction
            //TASK 1.3.1 ~ Steps forward until it sees a wall ahead
            while (robot.look(IRobot.AHEAD) != (IRobot.WALL) && !changeDirection) {
              //TASK 1.2.1 ~ Log direction of steps relative to start direction
              //             then logs aheaad for all futher movements until a wall is reached
              robot.getLogger().log(IRobot.AHEAD + rand);
              rand = 0;

              robot.advance();
              // wait for a while if we are supposed to
              if (delay > 0)
                  robot.sleep(delay);

              //TASK 1.3.2 ~ 12.5% of the time changeDirection will be true
              //             and robot will exit while and face a new direction
              changeDirection = (Math.random() <= 0.125);
            }

       }
    }

    // this method returns a description of this controller
    public String getDescription() {
       return "A controller which randomly chooses where to go";
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
