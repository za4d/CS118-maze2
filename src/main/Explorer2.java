import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// TODO[ !!! ] LOOK around method to group all the loops in dead end corridor ...
// TODO[ ? ] MAX number of steps
// TODO: BEAUTIFY!!
public class Explorer2 implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;

    // String for logging movements to terminal
    private String log;

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

        // Make sure robot doesnt start at a dead dead,
        // else it will start backtracking with no junctions
        robot.face(deadEnd());
        robot.advance();
        if (delay > 0) robot.sleep(delay*2);
        //TODO: remove Info
        // Info.all();


        //Reset Junction Array and Counter to 0 for new junctions
        if (robot.getRuns() == 0) {
            //initialse new data store
            this.robotData = new RobotData();
        }

        //Seperates terminal outputs of different Runs
        System.out.println("\n\n\n\n\n\n Maze Started:\n");
        System.out.println("POS\tEXPLR\tDIR\t\n");
        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
System.out.print(" ("+robot.getLocation().x+","+robot.getLocation().y+")\t");//TEMP
System.out.print(mode.isExploring()+"\t");//TEMP
            if (mode.isExploring()) {
                direction = exploreControl();
            }  else {
                direction = backtrackControl();
            }
System.out.print(directionToString(direction)+"\t");//TEMP
            robot.face(direction);
            robot.advance();
            System.out.println("");
            //TODO: Reset Delay to normal
            // wait for a while if we are supposed to
            if (delay > 0)
                robot.sleep(delay*2);
        }
    }



    // EXPLORE CONTROLLER
    public int exploreControl() {
        if(passageExits(Directions.All.ordered()) == 0){
            mode = Mode.BackTrack;
        }


        int exits = nonwallExits(Directions.All.shuffled());

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

                return crossroad();
            default:
                return IRobot.CENTRE;
        }
    }


//TODO[ !!! ] Cleanup passage exits test an non walls test
    // BACKTRACK CONTROLLER
    public int backtrackControl() {

        // Look for unexplored passages (Random order)
        for (int dir : Directions.All.shuffled() ) {
            // If passsage found...
            if (robot.look(dir) == IRobot.PASSAGE) {
                // ...set mode to Explore
                mode = Mode.Explore;
                // ...and go down the unexplored passage
                return dir;
            }
        }

        //If theres no unexplored passages ...
        int exits = nonwallExits(Directions.All.shuffled());

        switch (exits) {
            case 1:
                return deadEnd();
            case 2:
                return corridor();
            case 3:
            case 4:
                // When the robot is at a junction or corridor,
                // Search robotData for the current junction
                Junction junc = robotData.findJunction(robot.getLocation());
                // return opposite direction to the heading when juctions was fist entered

                System.out.print("\t* Revisted -- Junction "+junc.getID()+" -- Returning "+headingToString(reverseHeading(junc.getArrivalHeading())));//heading "+headingToString(junc.getArrivalHeading())+" -- "+junc.position.toString());

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
            //TODO: Delete print?
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

    //TODO[ ! ] Fix comments for direction array changes
    //Note: methods are public to allow gradle tests

    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position

    public int nonwallExits(int[] directions) {
        int exits = 4;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased

        for (int dir : directions ) {
            if (robot.look(dir) == IRobot.WALL) exits--;
        }
        return exits;
    }

    // public int nonwallExits() {
    //     return nonwallExits(Directions.All.shuffled());
    // }

    public int passageExits(int[] directions) {
        int exits = 0;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased

        for (int dir : directions ) {
            if (robot.look(dir) == IRobot.PASSAGE) exits++;
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

    //TODO[ ! ] (COMBINE EXPLANATIONS)Combine junction and crossroads?
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
