import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// QUESTION MAX number of steps
// [done] TODO Go through commments (after major changed have been made)
// [done] TODO LOOK around method to group all the loops in deadend corridor ...
// NOTE BEAUTIFY!!
// [done] TODO test todo labels

// [done] REVIEW: Fix tab from 4 to 8

public class Explorer implements IRobotController {
  // the robot in the maze
  private IRobot robot;

  // a flag to indicate whether we are looking for a path
  private boolean active = false;

  // logging active flag
  private boolean logging = false;

  // a value (in ms) indicating how long we should wait between moves
  private int delay;

  // RobotData to create and store the data on the junctions the robot encounters
  private RobotData robotData;

  // Observer concerns current state of the maze in the given directions
  private Observer lookAllAround; // (All directions)
  private Observer lookForwards; // (All directions EXCLUDING BEHIND)

  // Mode Constants
  private Mode mode;
  enum Mode {
    Explore(true), BackTrack(false);

    public boolean isExploring;

    Mode(boolean isExploring) {
      this.isExploring = isExploring;
    }

  }

// BUG FIX start stuck bug see test play ground


  /*
   * This method is called when the "start" button is clicked
   */
  public void start() {
    this.active = true;

    // Start robot on Exploring mode
    mode = Mode.Explore;

    // Set Up for new maze,
    // - Reset Junction Array to 0,
    // - Clears terminal and print logger headings
    if (robot.getRuns() == 0) {
      this.robotData = new RobotData();
    }

    // direction varible the robot will move toward in a given step
    int direction = IRobot.CENTRE;

    // adds starting point as as first junction (direction is reverse)
    robotData.addJunction(robot.getLocation(), IRobot.CENTRE);

    // Until the robot reaches the Goal ...
    while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {

      // Count number of Non Wall tiles (Exits)
      switch (lookAllAround.countExits()) {
        case 1:// when at a dead-end...
          direction = deadEnd();
          break;
        case 2:// when in a coridoor...
          direction = corridor();
          break;
        case 3:// when at a junction or crossroads... (combined into 1 'crossroad' function)
        case 4:
          // Send junction to robotData which updates structure if neccesery
          robotData.update(robot.getLocation(), robot.getHeading());

          // Then set direction according to mode.
          // if Exploring use exploreControl, else use backtrackControl
          direction = (mode.isExploring)? exploreControl() : backtrackControl();
          break;
        }

      // Wait
      if (delay > 0)
        robot.sleep(delay);

      //[done] REVIEW Explain how it prints everything

      // Log Explorer State
      // (Position, Mode, Directions and any reports from Junction data store)
      logStep(direction);

      // take a step
      robot.face(direction);
      robot.advance();
    }
  }



  /*
   * Exploring Mode Controller
   */
  public int exploreControl() {
    // If there's NO 'Unexplored' Passage, switch to BackTrack mode
    if (!lookAllAround.isThereA(IRobot.PASSAGE)) {
      mode = Mode.BackTrack;
      return backtrackControl();
    }
    // Else compute and return appropriate direction
    return crossroad();
  }



  //TODO[done]  Cleanup passage exits test an non walls test


  /*
   * Back-Tracking Mode Controller
   */
  public int backtrackControl() {
    // If there IS an 'Unexplored' Passage, switch to Explore mode
    if (lookForwards.isThereA(IRobot.PASSAGE)) {
      mode = Mode.Explore;
      return exploreControl();
    }

    // Search get current junction from robotData
    Junction junc = robotData.getJunction(robot.getLocation());

    // Else exit junction from where you first entered it
    robot.setHeading(reverseHeading(junc.arrivalHeading));
    return IRobot.AHEAD;
  }

  //REVIEW[done]  Fix comments for direction array changes
  //NOTE methods are public to allow gradle tests

  // returns a number indicating how many non-wall exits there
  // are surrounding the robot's current position




  /*
    DEAD-END: 1 Exit
     #   #
     # V #
     # # #
    Finds the 1 possible exit.
  */
  public int deadEnd() {
    // returns direction of the (single) Non Wall exit to go back down
    return lookAllAround.nextRandomExit();
  }


  /*
    CORRIDOR: 2 Exits
     #   #
     # V #
     #   #
    Randomly chooses any 'forwards' direction (doest around behind)
  */
  public int corridor() {
    return lookForwards.nextRandomExit();
  }


  /*
    JUNCTION or CROSSROADS: 3-4 Exit
     #   #
     # V
     #   #
    Randomly chooses any 'Unexplored' path.
    If none exist randomly chooses out of Explored paths
  */
  public int crossroad() {
    if (lookForwards.isThereA(IRobot.PASSAGE)) {
      return lookForwards.nextRandom(IRobot.PASSAGE);
    }
    return lookForwards.nextRandomExit();
  }


  public int nonwallExits() {
    return lookAllAround.countExits();
  }


  private void logStep(int direction) {
    if (this.logging) {
      // get current position
      String pos = "("+robot.getLocation().x+","+robot.getLocation().y+")";
      // get current mode
      String mod = mode.name();
      // get current Directions
      String dir = directionToString(direction);
      // get any junction array reports
      String log = robotData.log;
      //clear log
      robotData.log = "";

      // Set Table format for Log
      String format = " %-10s %-10s %-10s %s%n";
      // print to terminal
      System.out.printf(format, pos, mod, dir, log);
    }
  }


  /*
   * Static Methods
   */

  // Returns opposite heading
  public static int reverseHeading(int heading) {
    switch(heading) {
      case IRobot.NORTH:
        return IRobot.SOUTH;
      case IRobot.EAST:
        return IRobot.WEST;
      case IRobot.SOUTH:
        return IRobot.NORTH;
      case IRobot.WEST:
        return IRobot.EAST;
      default:
        return IRobot.CENTRE;
    }
  }

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


  //Convert integer heading to string
  public static String directionToString(int dir) {
    switch(dir) {
      case IRobot.AHEAD:
        return "Ahead";
      case IRobot.RIGHT:
        return "Right";
      case IRobot.BEHIND:
        return "Behind";
      case IRobot.LEFT:
        return "Left";
      default:
        return "ERR";
    }
  }

  // Converts point to reader friendly coordinates
  public static String locationToString(Point p) {
    return "("+p.x+","+p.y+")";
  }

  /*
   * Default Methods
   */

  // this method returns a description of this controller
  public String getDescription() {
    return "A controller which explores the maze in a structured way";
  }



  // sets the delay
  public void setDelay(int millis) {
    delay = millis;
  }



  // gets the current delay
  public int getDelay() {
    return delay;
  }



  // stops the controller
  public void reset() {
    active = false;
    System.out.print("\n\n\n\n\n ");
    System.out.print("Explorer Log:\n ");
    // print Log heading
    System.out.print("POS \tEXPLR \tDIR \t\n\n");
  }



  // sets the reference to the robot
  public void setRobot(IRobot robot) {
    this.robot = robot;
    //also initialse Observer objects
    this.lookAllAround = new Observer(this.robot, new int[]{ IRobot.AHEAD,
                                                              IRobot.LEFT,
                                                              IRobot.RIGHT,
                                                              IRobot.BEHIND });

     this.lookForwards = new Observer(this.robot, new int[]{ IRobot.AHEAD,
                                                              IRobot.LEFT,
                                                              IRobot.RIGHT });

  }



}
