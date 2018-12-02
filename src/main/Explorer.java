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

    // Robot exploration mode: true = Explore, false = Backtrack
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

            if (mode.isExploring()) direction = exploreControl();

            robot.face(direction);
            robot.advance();

            //TODO: Reset Delay to normal
            if (delay > 0)
            robot.sleep(delay*2);
        }
    }


    public int exploreControl() {

        // wait for a while if we are supposed to
        int exits = nonwallExits();

        switch (exits) {
            case 1:
                mode = Mode.Backtrack;
                return deadEnd();
            case 2:
                return corridor();
            case 3:
            case 4:
                // When the robot is at a junction or corridor,
                // search robotData for the junction
                if (robotData.findJunction(robot.getLocation()) == -1) // If junction not in data store (position = -1)...
                robotData.addJunction(robot.getLocation(), robot.getHeading());//...then add it

                // Test if junctions are correctly recorded
                robotData.printJunction();
                return junction();
            default:
                return IRobot.CENTRE;
        }
    }

    //TODO:: Fix comments for direction array changes
    //Note: methods are public to allow gradle tests

    // returns a number indicating how many non-wall exits there
    // are surrounding the robot's current position
    public int nonwallExits() {
        int exits = 4;
        // Direction.values() is an array of values Direction in the enumeration
        // Each direction is tested, and if its a wall the number of exits is decreased

        for (int dir : Directions.All.shuffled() ) {
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



    // // ENUMS DECLARATIONS

}


// TODO:: Seperate Classes Seperate files?

//TODO:: Descibe how i recorded the juntions
class RobotData {
    // size of Junction array
    private static int maxJunctions = 900;
    // counter for current empty array index
    private static int juncCount;
    // Array for storing list of juctions visited
    private Junction[] junctionList = new Junction[maxJunctions];

    // initialise RobotData object and set junction counter to 0
    RobotData() {
        resetJuncCount();
    }

    // reset junction Counter to 0
    public void resetJuncCount() {
        this.juncCount = 0;
    }

    // return current juncCounter value
    public int getJuncCount() {
        return this.juncCount;
    }

    //Adds juction to the Array and increments the counter
    public void addJunction(Point position, int arrivalHeading) {
        this.junctionList[this.juncCount++] = new Junction(position, arrivalHeading);
    }

    //Adds juction to the Array and increments the counter
    public void addJunction(int x, int y, int arrivalHeading) {
        addJunction(new Point(x,y), arrivalHeading);
    }


    //Search junction list for matching position Point
    //TODO::(searches backwards as your more likely to cros a junction you recently found)
    public int findJunction(Point junc) {
        for (int i=0; i < getJuncCount(); i++) {
            if (this.junctionList[i].position.equals(junc)) return i;
        }
        return -1;
    }

    //Search junction list for matching X and Y coordinates
    public int findJunction(int x,int y) {
        // create Point object for the position
        Point junc = new Point(x,y);
        //Search data store for matching point
        return findJunction(junc);
    }

    //Prints juction i details to terminal
    public void printJunction(int i) {
        // print out Coordinates of a junction in the array
        System.out.println("Junction "+i+" -- heading "+headingToString(this.junctionList[i].arrivalHeading())+" -- "+this.junctionList[i].position.toString());
    }

    public void printJunction() {
        //if no index specifed print most recent junction
        printJunction(this.juncCount-1);
    }

    //Convert integer heading to string
    public String headingToString(int dir) {
        switch(dir) {
            case 1000:
                return "North";
            case 1001:
                return "East";
            case 1002:
                return "South";
            case 1003:
                return "West";
            default:
                return "INVALID";
        }
    }


}



class Junction {
    // Coordinate of junction in a point object
    protected Point position;
    // initial heading when robot arrived at junction
    private int arrivalHeading;

    //Constructs a Junction given a Point and Heading
    Junction(Point position,int arrivalHeading) {
        this.position = position;
        this.arrivalHeading = arrivalHeading;
    }

    //Constructs a Junction given x adn y coordinates and Heading
    Junction(int x, int y,int arrivalHeading) {
        this.position = new Point(x,y);
        this.arrivalHeading = arrivalHeading;
    }

    //returns the arrival Heading of a junction
    public int arrivalHeading() {
        return this.arrivalHeading;
    }

    //TODO:: remove position getter?
    public Point position() {
        return this.position;
    }


}
