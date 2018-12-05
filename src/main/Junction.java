import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// REVIEW Seperate Classes Seperate files?

// REVIEW Descibe how i recorded the juntions

//
public class Junction {
  //REVIEW Why is it PUBLIC
  // index number of junction
  public final int index;

  // Coordinate of junction in a point object
  public final Point position;

  // initial heading when robot arrived at junction
  public final int arrivalHeading;

  // initial heading when robot LAST exited at junction
  private int departureHeading = IRobot.CENTRE;

  //Constructs a Junction given a Point and Heading
  Junction(int index, int arrivalHeading, Point position) {
    this.index = index;
    this.arrivalHeading = arrivalHeading;
    this.position = position;
  }

  // // Null Junction
  // Junction() {
  //   this.index = -1;
  //   this.position = new Point(-1,-1);
  //   this.arrivalHeading = IRobot.CENTRE;
  // }

  //NOTE remove x y?
  // //Constructs a Junction given x adn y coordinates and Heading
  // Junction(int x, int y, int arrivalHeading) {
  //   this.index = index;
  //   this.position = new Point(x,y);
  //   this.arrivalHeading = arrivalHeading;
  // }

  // //returns the arrival Heading of a junction
  // public int getArrivalHeading() {
  //   return this.arrivalHeading;
  // }

  // public Point getPosition() {
  //   return this.position;
  // }

  // public int getIndex() {
  //   return this.index;
  // }

}
