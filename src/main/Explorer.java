import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// TODO Go through commments (after major changed have been made)
// TODO LOOK around method to group all the loops in deadend corridor ...
// QUESTION MAX number of steps
// NOTE BEAUTIFY!!
// [DONE]TODO test todo labels

// REVIEW: Fix tab from 4 to 8

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

    // All directions
    private MazeSurroundings lookAllAround;

    // All directions Excluding BEHIND
    private MazeSurroundings lookForwards;

    // this method is called when the "start" button is clicked
    // in the user interface
    public void start() {
        this.active = true;

        // Start robot on Exploring mode
        mode = Mode.Explore;

        // direction varible the robot will move toward in a given step
        int direction = IRobot.CENTRE;

        // // Make sure robot doesnt start at a dead dead,
        // // else it will start backtracking with no junctions (gets stuck)
        // robot.face(deadEnd());
        // robot.advance();
        // if (delay > 0) robot.sleep(delay*2);
        //NOTE remove Info
        // Info.all();


        //Reset Junction Array and Counter to 0 for new junctions
        if (robot.getRuns() == 0) {
            // Initialse new data store,
            // Clears terminal and adds log headings
            this.robotData = new RobotData();
        }

        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {

            // //TODO remove log Print or make cleaner method
            // System.out.print(" ("+robot.getLocation().x+","+robot.getLocation().y+")\t");//TEMP
            // System.out.print(mode.isExploring()+"\t");//TEMP

            if (mode.isExploring()) {
                direction = exploreControl();
            }  else {
                direction = backtrackControl();
            }

            //NOTE Reset Delay to normal
            // wait for a while if we are supposed to

            if (delay > 0)  robot.sleep(delay*2);

            // //TODO delay BEFORE logging
            // System.out.print(directionToString(direction)+"\t");//TEMP
            //REVIEW Explain how it prints everything
            logStep(direction);

            robot.face(direction);
            robot.advance();
        }
    }



    // EXPLORE CONTROLLER
    public int exploreControl() {
        //  If there No unexplored exits (Passages), switch to BackTrack mode
        if (!lookAllAround.isThereA(IRobot.PASSAGE)) mode = Mode.BackTrack;

        // Count number of (Non Wall) Exits
        switch (lookAllAround.countExits()) {
            case 1:
            return deadEnd();

            case 2:
            return corridor();

            case 3:
            case 4:

            // When the robot is at a junction or corridor,
            // Search robotData for a junction in current position
            robotData.update( robot.getLocation() , robot.getHeading() );
            //
            // // If returned junction ID (array index) is -1 then junction not in array...
            // if (junc.getIndex() == -1)
            // robotData.addJunction(robot.getLocation(), robot.getHeading());//...so add junction to array

            // //TEMP Manual check for if junctions are correctly recorded
            // robotData.printJunction();

            return crossroad();


            default:
            return IRobot.CENTRE;
        }
    }


    //TODO Cleanup passage exits test an non walls test
    // BACKTRACK CONTROLLER
    public int backtrackControl() {

        // Look for unexplored passages (Random order)
        if (lookForwards.isThereA(IRobot.PASSAGE)) {
            // If there's any Passages, set mode to Explore
            mode = Mode.Explore;
            // and randomly go down the unexplored passage
            return lookForwards.nextRandom(IRobot.PASSAGE);
        }

        // Count number of (Non Wall) Exits
        switch (lookAllAround.countExits()) {
            case 1:
            return deadEnd();
            case 2:
            return corridor();
            case 3:
            case 4:
            // When the robot is at a junction or corridor,
            // Search robotData for the current junction
            Junction junc = robotData.getJunction(robot.getLocation());
            // return opposite direction to the heading when juctions was fist entered

            // //TEMP
            // System.out.print("\t* Revisted -- Junction "+junc.getIndex()+" -- Returning "+headingToString(reverseHeading(junc.getArrivalHeading())));//heading "+headingToString(junc.getArrivalHeading())+" -- "+junc.position.toString());
            // System.out.print("\t* Revisted -- Junction "+junc.getIndex()+" -- Arrival: "+headingToString(junc.getArrivalHeading())+" -- "+junc.position.toString());

            robotData.update( robot.getLocation() , robot.getHeading() );


            robot.setHeading(reverseHeading(junc.getArrivalHeading()));
            return IRobot.AHEAD;

            default:
            return IRobot.CENTRE;
        }
    }

    //Returns opposite heading
    public int reverseHeading(int heading) {
        switch(heading) {
            case IRobot.NORTH:
            return IRobot.SOUTH;
            case IRobot.EAST:
            return IRobot.WEST;
            case IRobot.SOUTH:
            return IRobot.NORTH;
            case IRobot.WEST:
            return IRobot.EAST;
            default:
            //NOTE Delete print?
            System.out.print("reverseHeading(Invalid Argument)");
            return IRobot.CENTRE;
        }
    }

    //Convert integer heading to string
    public static String headingToString(int dir) {
        switch(dir) {
            case IRobot.NORTH:
            return "North";
            case IRobot.EAST:
            return "East";
            case IRobot.SOUTH:
            return "South";
            case IRobot.WEST:
            return "West";
            default:
            return "ERR";
        }
    }

    //Convert integer heading to string
    public static String directionToString(int dir) {
        switch(dir) {
            case IRobot.AHEAD:
            return "AHEAD";
            case IRobot.RIGHT:
            return "RIGHT";
            case IRobot.BEHIND:
            return "BEHIND";
            case IRobot.LEFT:
            return "LEFT";
            default:
            return "ERR";
        }
    }

    //REVIEW Fix comments for direction array changes
    //Note: methods are public to allow gradle tests

    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position




    /* DEADEND: number of Exits = 1 (back the way you came)
    e.g.    #   #
    # V #
    # # #

    Assumes there only 1 non wall (its a dead end) and returns the direction that exit. */
    public int deadEnd() {
        // returns direction of the (single) Non Wall exit to go back down
        return lookAllAround.nextRandomExit();
    }


    /* CORRIDOR: number of Exits is 2
    e.g.    #   #
    V #
    # # #

    There 2 exit and 1 is BEHIND you (which you shouldnt take),
    therefore we search the remaining 3 directions for the exit that isnt backtracking. */
    public int corridor() {
        // Randomly chooses one of the 2 Non Wall exits
        return lookForwards.nextRandomExit();
    }

    //REVIEW (COMBINE EXPLANATIONS)Combine junction and crossroads?
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
    public int crossroad() {
        // make a randomised array of directions (which arn't backwards)...
        // Look around for any unexplored corridors
        if (lookForwards.isThereA(IRobot.PASSAGE)) {
            // If there is, randomly choose and return one
            return lookForwards.nextRandom(IRobot.PASSAGE);
        };

        // Wall all surrounding paths have been explored,
        // choose a random direction that isnt a wall
        return lookForwards.nextRandomExit();
    }


    private void logStep(int direction){
        /*
        - position
        - Mode
        - Heading (Not r_dir!)
        - Junction changes
        */
        // String format = "%-40s%s%n";
        // System.out.printf(format, var1, var1);
        String format = " %-10s %-10s %-10s %s%n";
        // log current position
        String pos = "("+robot.getLocation().x+","+robot.getLocation().y+")";
        // log current position
        String mod = mode.name();
        String dir = directionToString(direction);
        String log = robotData.getLog();
        //Print the step
        System.out.printf(format, pos, mod, dir, log);
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
    //TODO Remove resetJuncCount
    public void reset() {
        active = false;
        this.robotData.resetJuncCount();
    }



    // sets the reference to the robot
    public void setRobot(IRobot robot) {
        this.robot = robot;
        //also initialse MazeSurroundings objects
        this.lookAllAround = new MazeSurroundings(this.robot, new int[]{IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT, IRobot.BEHIND});
        this.lookForwards = new MazeSurroundings(this.robot, new int[]{IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT});
    }


    public int nonwallExits() {
        return lookAllAround.countExits();
    }

}
