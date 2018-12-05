import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// REVIEW Seperate Classes Seperate files?
// REVIEW Descibe how i recorded the juntions
// REVIEW Why is it PUBLIC

//
public class Junction {
  // CONSTANTS:
  // index number of junction
  public final int index;
  // Coordinate of junction in a point object
  public final Point location;
  // initial heading when robot arrived at junction
  public final int arrivalHeading;

  // VARIABLE:
  // initial heading when robot LAST exited at junction
  private int departureHeading = IRobot.CENTRE;

  //Constructs a Junction given a Point and Heading
  Junction(int index, int arrivalHeading, Point location) {
    this.index = index;
    this.arrivalHeading = arrivalHeading;
    this.location = location;
  }

}
