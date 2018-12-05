import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Stack;

//REVIEW static?
//TODO remove TEMP

public class Recorder extends RobotData {
  //TEMP
  public Stack<Junction> getList() {
    return junctionList;
  }
  //TEMP

  // counter for number of junctions in stack
  private int juncCount;
  // Stack for storing list of juctions visited
  private Stack<Junction> junctionList;


  // initialises Recorder in the same way as RobotData
  Recorder() {
    super();
    this.juncCount = 0;
    this.junctionList = new Stack<Junction>();
  }

  //Adds juction to the Array and increments the counter
  public void addJunction(Point location, int arrivalHeading) {
    Junction junc = new Junction(juncCount, arrivalHeading, location);
    junctionList.push(junc);
    juncCount++;
  }

  // returns Junction at the top of stack without removing it
  public Junction getJunction() {
    return junctionList.peek();
  }


  // Updates junction if its New
  public void update(Point location, int arrivalHeading) {
    Junction junc = junctionList.peek();
    // Search list for a juction in given location
    if (junc.location.equals(location)) {
      // Update the log to say report A juctions been REVISTED
      log =   "Revisted Junction " + junc.index
      + " - Arrival: " + Explorer.headingToString(junc.arrivalHeading)
      + " - " + Explorer.locationToString(junc.location);
    } else {
      addJunction(location, arrivalHeading);
      // Update the current log to say new junction added
      log =  "New Junction "   + this.junctionList.peek().index
      + " - Arrival: "   + Explorer.headingToString(junctionList.peek().arrivalHeading)
      + " - "      + Explorer.locationToString(junctionList.peek().location);
    }
  }


  public Junction removeJunction() {
    Junction junc = junctionList.pop();
    juncCount--;
    // Reupdate the log to say Removed instead of revisited
    log =   "REMOVED Junction " + junc.index
    + " - Arrival: " + Explorer.headingToString(junc.arrivalHeading)
    + " - " + Explorer.locationToString(junc.location);

    return junc;
  }



}
