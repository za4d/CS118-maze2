import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Stack;

//REVIEW static?
//TODO remove TEMP

public class GrandRecorder extends Recorder3 {

  private Junction[] routeToFinish;

  // flags if robot recognises route
  private boolean routeKnown;

  // initialises Recorder in the same way as RobotData
  GrandRecorder() {
    super();
    this.routeKnown = false;
  }

  public boolean isRouteKnown() {
    return this.routeKnown;
  }

  public Junction[] getRoute() {
    return this.routeToFinish;
  }

  public void storeRoute() {
    // set array size for final route
    this.routeToFinish = new Junction[super.getJuncCount()];
    Stack<Junction> list = super.getJunctionList();

    for (int i = routeToFinish.length - 1 ; i>=0; i--) {
      this.routeToFinish[i] = list.pop();
    }
    this.routeKnown = true;
  }

}
