import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Stack;

//REVIEW static?
//TODO remove TEMP

public class Recorder3 extends Recorder {
  private boolean updateDepartureFlag = false;
  // counter for number of junctions in stack
  private int juncCount;
  // Stack for storing list of juctions visited
  private Stack<Junction> junctionList = new Stack<Junction>();


  // initialises Recorder in the same way as RobotData
  Recorder3() {
    super();
    this.juncCount = 0;
    // this.junctionList = new Stack<Junction>();
  }

  public int getJuncCount() {
    return this.juncCount;
  }

  public Stack<Junction> getJunctionList() {
    return this.junctionList;
  }


  // returns Junction at the top of stack without removing it
  public Junction getJunction() {
    return junctionList.peek();
  }


  //Adds juction to the Array and increments the counter
  public void addJunction(Point location, int arrivalHeading) {
    Junction junc = new Junction(juncCount, arrivalHeading, location);
    junctionList.push(junc);
    juncCount++;
  }


  // Updates junction if its New
  public void update(Point location, int arrivalHeading) {
    this.updateDepartureFlag = true;

    if (junctionList.empty()) { // add start `junction`
      addJunction(location, arrivalHeading);
      updateDeparture(arrivalHeading);
      log =  "Start of Maze " + junctionList.peek().toString();
      return;
    }

    Junction  junc = junctionList.peek();

    if (junc.location.equals(location)) {
      log =   "Revisted "; // Update the log to say report A juctions been REVISTED

    } else {
      addJunction(location, arrivalHeading); //Add new Junction
      log =  "New ";// Update the current log to say new junction added
    }
  }

  public void updateDeparture(int departureHeading) {
    if (updateDepartureFlag){
      this.updateDepartureFlag = false;
      Junction junc = junctionList.pop();
      junc.setDepartureHeading(departureHeading);
      junctionList.push(junc);
      log += junctionList.peek().toString();
    }
  }


  public Junction removeJunction() {
    Junction junc = junctionList.pop();
    juncCount--;
    // Reupdate the log to say Removed instead of revisited
    log =   "REMOVED " + junc.toString();
    return junc;
  }



}
