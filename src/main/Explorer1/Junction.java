import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// REVIEW Seperate Classes Seperate files?
// REVIEW Descibe how i recorded the juntions
// REVIEW Why is it PUBLIC

//TODO GROUP VARIABLE!!!

public class Junction {
  // CONSTANTS:
  public final int index; // index number of junction
  public final Point location; // Coordinate of junction in a point object
  public final int arrivalHeading; // initial heading when robot arrived at junction
  // VARIABLE:
  public int departureHeading; // initial heading when robot LAST exited at junction

  //Constructs a Junction given a Point and Heading
  Junction(int index, int arrivalHeading, Point location) {
    this.index = index;
    this.arrivalHeading = arrivalHeading;
    this.location = location;
  }


  public void setDepartureHeading(int departureHeading) {
    this.departureHeading = departureHeading;
  }


  public String toString() {
    return "Junction " + this.index
           + " - Arrival: " + Explorer.headingToString(this.arrivalHeading)
           + " - Departure: " + Explorer.headingToString(this.departureHeading)
           + " - " + Explorer.locationToString(this.location);
  }

}
