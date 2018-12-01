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

    private static final int[] allDirections = {IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT, IRobot.BEHIND}; // BEHIND Last so its only checked in the worst case
    private static final int[] forwardDirections = {IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT};


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


    /* DEADEND: number of Exits = 1 (back the way you came)
    e.g.    #   #
            # V #
            # # #

    Assumes there only 1 non wall (its a dead end) and returns the direction that exit. */
    public int deadEnd() {
        for (int dir : this.allDirections) {
            if (robot.look(dir) != IRobot.WALL) return dir;
        }
         return -1;
    }


    /* CORRIDOR: number of Exits is 2
    e.g.    #   #
              V #
            # # #

    There 2 exit and 1 is BEHIND you (which you shouldnt take),
    therefore we search the remaining 3 directions for the exit that isnt backtracking. */
    public int corridor() {
        for (int dir : this.forwardDirections) {
            if (robot.look(dir) != IRobot.WALL) return dir;
        }
         return -1;
    }


    /* JUNCTION: number of Exits is 3
    e.g.    #   #
              V  
            # # #

    First we look for a route that we havent taken before,
    Else if there isnt any we randomly choose from the others.*/
    public int junction() {
        // Look around for any unexplored corridors
        for (int dir : this.allDirections) {
            if (robot.look(dir) == IRobot.PASSAGE) return dir;
        }

        // If all exits have been searched before,
        // make a randomies array of directions (which arn't backwards)...
        int[] direction = randRotate(this.forwardDirections);

        // ... and choose a direction that isnt a wall
        for (int dir : this.allDirections) {
            if (robot.look(dir) != IRobot.WALL) return dir;
        }

         return -1;
    }


    /* CROSSROADS: number of Exits is 4
    e.g.    #   #
              V
            #   #

    */
    public int crossroads() {
        return 4;
    }



    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position
    public int nonwallExits() {
        int exits = 4;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased
        for (int dir : this.allDirections ) {
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


    // returns and array which has been ROTATED a random number of times
    private static int[] randRotate(int[] array) {
        int[] rotatedArray = new int[array.length]; // Rotated array

        int r = (int)( Math.random() * array.length ); // random postion rotated array will start from

        for (int i = 0; i < array.length; i++) {
            rotatedArray[i] = array[(r + i) % array.length];
        }
        return rotatedArray;
    }



    // // ENUMS DECLARATIONS
    //
    // public void look
}
