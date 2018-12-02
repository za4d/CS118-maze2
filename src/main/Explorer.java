import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

//TODO:: BEAUTIFY!!
public class Explorer implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;

    // RobotData to create and store the data on the junctions the robot encounters
    private RobotData robotData;

    // Robot exploration mode: true = Explore, false = BackTrack
    private Mode mode;


    // this method is called when the "start" button is clicked
    // in the user interface
    public void start() {
        this.active = true;
        // Start robot on Exploring mode
        mode = Mode.Explore;

        // direction varible the robot will move toward in a given step
        int direction = IRobot.CENTRE; // initialise as centre i.e. No Direction

        //TODO: remove Info
        // Info.all();


        //Reset Junction Array and Counter to 0 for new junctions
        if (robot.getRuns() == 0) {
            //initialse new data store
            this.robotData = new RobotData();
        }

        //Seperates terminal outputs of different Runs
        System.out.println("\n\n\n Maze Started:\n");

        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {

            if (mode.isExploring()) {
                direction = exploreControl();
            }  else {
                direction = backtrackControl();
            }

            robot.face(direction);
            robot.advance();

            //TODO: Reset Delay to normal
            // wait for a while if we are supposed to
            if (delay > 0)
                robot.sleep(delay*2);
        }
    }



    // EXPLORE CONTROLLER
    public int exploreControl() {

        int exits = nonwallExits();

        switch (exits) {
            case 1:
                mode = Mode.BackTrack;
                return deadEnd();
            case 2:
                return corridor();
            case 3:
            case 4:
                // When the robot is at a junction or corridor,
                // Search robotData for a junction in current position
                Junction junc = robotData.findJunction(robot.getLocation());

                // If returned junction ID (array index) is -1 then junction not in array...
                if (junc.getID() == -1)
                    robotData.addJunction(robot.getLocation(), robot.getHeading());//...so add junction to array

                // Manual check for if junctions are correctly recorded
                robotData.printJunction();

                return junction();
            default:
                return IRobot.CENTRE;
        }
    }



    // BACKTRACK CONTROLLER
    public int backtrackControl() {
        
        int exits = nonwallExits();

        switch (exits) {
            case 1:
                return deadEnd();
            case 2:
                return corridor();
            case 3:
            case 4:
                // When the robot is at a junction or corridor,
                // Search robotData for a junction in current position
                Junction junc = robotData.findJunction(robot.getLocation());

                // If returned junction ID (array index) is -1 then junction not in array...
                if (junc.getID() == -1)
                    robotData.addJunction(robot.getLocation(), robot.getHeading());//...so add junction to array

                // Manual check for if junctions are correctly recorded
                robotData.printJunction();

                return junction();
            default:
                return IRobot.CENTRE;
        }
    }

    //Returns opposite heading
    public int reverseHeading(int h) {
        switch(h) {
            case IRobot.NORTH:
                return IRobot.SOUTH;
            case IRobot.EAST:
                return IRobot.WEST;
            case IRobot.SOUTH:
                return IRobot.NORTH;
            case IRobot.WEST:
                return IRobot.EAST;
            default:
                return IRobot.CENTRE;
        }
    }



    //TODO:: Fix comments for direction array changes
    //Note: methods are public to allow gradle tests

    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position
    public int nonwallExits() {
        return nonwallExits(Directions.All.shuffled());
    }

    public int nonwallExits(int[] directions) {
        int exits = 4;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased

        for (int dir : directions ) {
            if (robot.look(dir) == IRobot.WALL) exits--;
        }
        return exits;
    }


    public int beenbeforeExits() {
        int beenbeforeExits = 0;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased
        for (int dir : Directions.All.shuffled()  ) {
            if (robot.look(dir) == IRobot.BEENBEFORE) beenbeforeExits++;
        }
        return beenbeforeExits;
    }


    /* DEADEND: number of Exits = 1 (back the way you came)
    e.g.    #   #
            # V #
            # # #

    Assumes there only 1 non wall (its a dead end) and returns the direction that exit. */
    public int deadEnd() {
        for (int dir : Directions.All.shuffled() ) {
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
        for (int dir : Directions.Forwards.shuffled()) {
            if (robot.look(dir) != IRobot.WALL) return dir;
        }
         return -1;
    }

    //TODO:: (COMBINE EXPLANATIONS)Combine junction and crossroads?
    /* JUNCTION: number of Exits is 3
    e.g.    #   #
              V
            # # #

    First we look for a route that we havent taken before,
    Else if there isnt any we randomly choose from the others.*/
    /* CROSSROADS: number of Exits is 4
    e.g.    #   #
              V
            #   #

    Junction and Crossroad code are the same and therfore are combined*/
    public int junction() {
        // make a randomised array of directions (which arn't backwards)...
        // Look around for any unexplored corridors
        for (int dir : Directions.Forwards.shuffled()) {
            if (robot.look(dir) == IRobot.PASSAGE) return dir;
        }

        // If all exits have been searched before,
        // choose a random direction that isnt a wall
        for (int dir : Directions.Forwards.shuffled()) {
            if (robot.look(dir) != IRobot.WALL) return dir;
        }

         return -1;
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
        this.robotData.resetJuncCount();
    }



    // sets the reference to the robot
    public void setRobot(IRobot robot) {
        this.robot = robot;
    }



}
