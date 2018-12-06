import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
import java.util.Stack;//TEMP

//QUESTION Single loop double loop
//REVIEW Update after other Exploerers Clea`ned
/*
TASK 2.4 (GRANDFINALE):

*/
// TODO LOOK around method to group all the loops in dead end corridor ...
// QUESTION MAX number of steps
// NOTE Improve Depth First junction store efficiency
// NOTE Labal Tasks`


public class GrandFinale extends Explorer3    {
  private boolean active = false;
  private boolean routeKnown = false;
  private int index = 0;
  private IRobot robot;
  public Junction[] route;
  private int delay;



  public void start() {
    if (this.routeKnown) {
      this.delay = super.getDelay();
      this.robot = super.getRobot();
      active = true;
      int direction = getStep();
      while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
        switch (super.lookAllAround.countExits()) {
          case 1:// when at a dead-end...
            direction = super.deadEnd();
            break;
          case 2:// when in a coridoor...
            direction = super.corridor();
            break;
          case 3:// when at a junction or crossroads... (combined into 1 'crossroad' function)
          case 4:
            robot.setHeading(getStep());
            direction = IRobot.AHEAD;
          break;
        }

        // Wait
        if (delay > 0)
          robot.sleep(delay);

        robot.face(direction);
        robot.advance();
      }
    } else {
      super.start();
    }
    saveRoute();
  }


  public void saveRoute() {
    // set array size for final route
    this.route = new Junction[super.robotData.getJuncCount()];
    Stack<Junction> routeList = super.robotData.getJunctionList();

    for (int i = route.length-1; i >= 0; i--) {
      this.route[i] = routeList.pop();
      System.out.println(this.route[i].toString());
    }
    this.routeKnown = true;
  }

  public int getStep() {
    System.out.println(Explorer.headingToString(route[index].departureHeading));//temp
    return route[index++].departureHeading;
  }

}

  //
  //   public void reset() {
  //     // for ( int : ) {
  //
  //     }
  //     super.reset()
  //   }
