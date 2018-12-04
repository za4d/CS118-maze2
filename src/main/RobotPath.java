import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

//
public class RobotPath {
    // size of Junction array
    private static int maxJunctions = 900;
    // counter for current empty array index
    private static int juncCount;
    // Array for storing list of juctions visited
    private Junction[] junctionList = new Junction[maxJunctions];

    // initialise RobotPath object and set junction counter to 0
    RobotPath() {
        setJuncCount(0);
    }


    // Set Array index (JuncCounter)
    public void setJuncCount(int juncCount) {
        this.juncCount = juncCount;
    }

    // return current juncCounter value
    public int getJuncCount() {
        return this.juncCount;
    }

    //Adds juction to the Array and increments the counter
    public void addJunction(Point position, int arrivalHeading) {
        this.junctionList[this.juncCount] = new Junction(position, arrivalHeading, this.juncCount);
        this.juncCount++;
    }

    //Adds juction to the Array and increments the counter
    public void addJunction(int x, int y, int arrivalHeading) {
        addJunction(new Point(x,y), arrivalHeading);
    }


    //TODO[ ! ] (searches backwards as your more likely to cros a junction you recently found)
    //Search junction list for matching position Point
    // Search robotData list for a matches position
    // Returns the Junction object of
    public Junction findJunction(Point junc) {
        for (int i=0; i < getJuncCount(); i++) {
            if (this.junctionList[i].position.equals(junc)) return this.junctionList[i];
        }
        // If junction Not Found then it returns a Null junction
        return new Junction();
    }

    //Search junction list for matching X and Y coordinates
    public Junction findJunction(int x,int y) {
        // create Point object for the position
        Point junc = new Point(x,y);
        //Search data store for matching point
        return findJunction(junc);
    }

    //Prints juction i details to terminal
    public void printJunction(int i) {
        // print out Coordinates of a junction in the array
        System.out.print("Junction "+this.junctionList[i].getID()+" -- heading "+headingToString(this.junctionList[i].getArrivalHeading())+" -- "+positionToString(this.junctionList[i].getPosition()));
    }

    public void printJunction() {
        //if no index specifed print most recent junction
        printJunction(this.juncCount-1);
    }

    public void printPath() {
        // If no index specifed print most recent junction
        System.out.println("\n\n\n\tFINAL PATH:\n");
        for (int i=0 ; i < this.juncCount ; i++) {
            printJunction(i);
            System.out.println("");
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

    public static String positionToString(Point p) {
        return "("+p.x+","+p.y+")";
    }




}
