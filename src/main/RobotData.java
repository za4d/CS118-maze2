import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

//REVIEW static?
public class RobotData {
  // size of Junction array
  private static int maxJunctions = 900;
  // counter for current empty array index
  private int juncCount;
  // Array for storing list of juctions visited
  private Junction[] junctionList = new Junction[maxJunctions];
  // Stores string with the mode recent update to the database;
  private String log = "";

  // initialise RobotData object and set junction counter to 0
  RobotData() {
    this.juncCount = 0;

    //Seperates Logs from different Runs
    System.out.print("\n\n\n\n\n ");
    System.out.print("Explorer Log:\n ");
    // print Log heading
    System.out.print("POS \t");
    System.out.print("EXPLR \t");
    System.out.print("DIR \t");
    System.out.print("\n\n");
  }

  // Reset junction Counter to 0
  public void resetJuncCount() {
    this.juncCount = 0;
  }

  // return current juncCounter value
  public int getJuncCount() {
    return this.juncCount;
  }

  //Adds juction to the Array and increments the counter
  public void addJunction(Point position, int arrivalHeading) {
    this.junctionList[this.juncCount] = new Junction(this.juncCount, arrivalHeading, position);
    this.juncCount++;
  }

  // //Adds juction to the Array and increments the counter
  // public void addJunction(int x, int y, int arrivalHeading) {
  //   addJunction(new Point(x,y), arrivalHeading);
  // }


  //REVIEW (searches backwards as your more likely to cros a junction you recently found)
  //Search junction list for matching position Point
  // Search robotData list for a matching position (coordinates)
  // Returns the Junction object of
  public Junction getJunction(Point coordinates) {
    for (int i=0; i < this.juncCount; i++) {
      if (this.junctionList[i].position.equals(coordinates)) {
        return this.junctionList[i];
      }
    }
    // If junction no match found,
    // returns a NULL junction (index = -1)
    return new Junction(-1, IRobot.CENTRE, new Point(-1,-1));
  }


  // //Search junction list for matching postion
  // public Junction getJunction(int x,int y) {
  //   // create Point object for the position
  //   Point junc = new Point(x,y);
  //   //Search data store for matching point
  //   return getJunction(junc);
  // }


  // Updates junction list when appropriate
  public void update(Point position, int arrivalHeading) {
    // Searches list for a juction in given location
    Junction junc = getJunction(position);
    if (junc.index == -1) { // If juction NOT in list, //REVIEW
      addJunction(position, arrivalHeading);// Add it to the junctionList
      // Update the current log status
      log =  "New Junction "   + this.junctionList[this.juncCount-1].index
          + " - Arrival: "   + headingToString(this.junctionList[this.juncCount-1].arrivalHeading)
          + " - "      + positionToString(this.junctionList[this.juncCount-1].position);
    } else { // If juction NOT in list,
      // Update the log to say report A juctions been REVISTED
      log =   "Revisted Junction " + junc.index
          + " - Arrival: " + headingToString(junc.arrivalHeading)
          + " - " + positionToString(junc.position);


    }
  }


  public String getLog() {
    String copy = this.log;
    this.log = "";
    return copy;
  }


  // //REVIEW Delete printJunction
  // //Prints juction i details to terminal
  // public void printJunction(int i) {
  //   // print out Coordinates of a junction in the list
  //   System.out.print("Junction "+this.junctionList[i].getIndex()+" -- heading "+headingToString(this.junctionList[i].getArrivalHeading())+" -- "+positionToString(this.junctionList[i].position);
  // }

  //
  // public void printJunction() {
  //   //if no index specifed print most recent junction
  //   printJunction(this.juncCount-1);
  // }




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
