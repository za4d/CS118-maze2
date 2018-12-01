import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

public class Explorer implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;

    private static final int[] directions = {IRobot.AHEAD, IRobot.BEHIND, IRobot.LEFT, IRobot.RIGHT};



    // this method is called when the "start" button is clicked
    // in the user interface
    public void start() {
        this.active = true;
        int exits;
        int direction = -1;

        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
            // wait for a while if we are supposed to
            exits = nonwallExits();

            switch (exits) {
                case 0:

                    break;
                case 1:
                    direction = deadEnd();
                    break;
                case 2:
                    direction = corridor();
                    break;
                case 3:
                    direction = junction();
                    break;
                case 4:
                    direction = crossroads();
                    break;
            }

            robot.face(direction);
            robot.advance();

            if (delay > 0)
            robot.sleep(delay);
        }
    }

    // What to do when number of surrounding walls is 1
    public int deadEnd() {

        for (int dir : this.directions) {
            //Assumes there only 1 non wall (its a dead end) and returns the direction that exit
            if (robot.look(dir) != IRobot.WALL) return dir;
        }
         return -1;
    }

    // What to do when number of surrounding walls is 2
    public int corridor() {
        return 2;
    }

    // What to do when number of surrounding walls is 3
    public int junction() {
        return 3;
    }

    // What to do when number of surrounding walls is 4
    public int crossroads() {
        return 4;
    }
    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position
    public int nonwallExits() {
        int exits = 4;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased
        for (int dir : this.directions ) {
            if (robot.look(dir) == IRobot.WALL) exits--;
        }
        return exits;
    }



    // this method returns a description of this controller
    public String getDescription() {
        return "A controller which explores the maze in a structured way";
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




    // // ENUMS DECLARATIONS
    //
    // public void look
}
