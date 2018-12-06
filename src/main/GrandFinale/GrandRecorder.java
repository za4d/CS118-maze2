import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Stack;

//REVIEW static?
//TODO remove TEMP

public class GrandRecorder extends Recorder3 {

  private Junction[] routeToFinish;

  // flags if robot recognises route
  private boolean knowsRoute;

  // initialises Recorder in the same way as RobotData
  GrandRecorder() {
    super();
    this.knowsRoute = false;
  }

  public Junction[] getRoute() {
    return this.routeToFinish;
  }

  public void storeRoute() {
    // set array size for final route
    this.routeToFinish = new Junction[super.getJuncCount()];
    Stack<Junction> list = super.getJunctionList();

    for (int i = routeToFinish - 1 ; i>=0; i--)
      this.routeToFinish[i] = list.pop();
    }
    this.knowsRoute = true
  }



  public Stack<Junction> getJunctionList() {
    return junctionList;
  }





}
