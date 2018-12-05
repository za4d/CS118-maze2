import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

//REVIEW static?
// REVIEW Make into recoder?
public class RobotData {
  // stores string with the mode recent update to the database;
  public String log = "";
  // counter for current empty array index
  private int juncCount;
  // array for storing list of juctions visited
  private Junction[] junctionList = new Junction[900];

  // initialise RobotData object and set junction counter to 0
  RobotData() {
    this.juncCount = 0;
  }

  // // Reset junction Counter to 0
  // public void resetJuncCount() {
  //   this.juncCount = 0;
  // }

  // // return current juncCounter value
  // public int getJuncCount() {
  //   return this.juncCount;
  // }

  //Adds juction to the Array and increments the counter
  public void addJunction(Point location, int arrivalHeading) {
    this.junctionList[juncCount] = new Junction(juncCount, arrivalHeading, location);
    juncCount++;
  }

  // Search robotData list for a matching the location (coordinates)
  public Junction getJunction(Point coordinates) {
    for (int i=0; i < juncCount; i++) {
      if (this.junctionList[i].location.equals(coordinates)) {
        return this.junctionList[i];
      }
    }
    // If no match found,
    // returns a 'Null' junction (index = -1)
    return new Junction(-1, IRobot.CENTRE, new Point(-1,-1));
  }


  // Updates junction if its New
  public void update(Point location, int arrivalHeading) {
    // Search list for a juction in given location
    Junction junc = getJunction(location);
    if (junc.index == -1) {
      addJunction(location, arrivalHeading);
      // Update the current log to say new junction added
      this.log = "New Junction "   + this.junctionList[juncCount-1].index
          + " - Arrival: "   + Explorer.headingToString(this.junctionList[juncCount-1].arrivalHeading)
          + " - "      + Explorer.locationToString(this.junctionList[juncCount-1].location);

    } else {
      // Update the log to say report A juctions been REVISTED
      this.log = "Revisted Junction " + junc.index
          + " - Arrival: " + Explorer.headingToString(junc.arrivalHeading)
          + " - " + Explorer.locationToString(junc.location);
    }
  }

}
