import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// TODO:: Seperate Classes Seperate files?

//TODO:: Descibe how i recorded the juntions

//
public class Junction {
    // index number of junction
    private int juncID;
    // Coordinate of junction in a point object
    protected Point position;
    // initial heading when robot arrived at junction
    private int arrivalHeading;

    //Constructs a Junction given a Point and Heading
    Junction(Point position, int arrivalHeading, int juncID) {
        this.juncID = juncID;
        this.position = position;
        this.arrivalHeading = arrivalHeading;
    }

    // Null Junction
    Junction() {
        this.juncID = -1;
        this.position = new Point(-1,-1);
        this.arrivalHeading = IRobot.CENTRE;
    }

    //TODO:: remove x y?
    //Constructs a Junction given x adn y coordinates and Heading
    Junction(int x, int y, int arrivalHeading) {
        this.juncID = juncID;
        this.position = new Point(x,y);
        this.arrivalHeading = arrivalHeading;
    }

    //returns the arrival Heading of a junction
    public int getArrivalHeading() {
        return this.arrivalHeading;
    }

    //TODO:: remove position getter?
    public Point getPosition() {
        return this.position;
    }

    public int getID() {
        return this.juncID;
    }

}
