import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

public class HomingController implements IRobotController {
    // the robot in the maze
    private IRobot robot;
    // a flag to indicate whether we are looking for a path
    private boolean active = false;
    // a value (in ms) indicating how long we should wait
    // between moves
    private int delay;


    // EXPLANATION: this method is called when the "start" button is clicked in the user interface
    public void start() {
        this.active = true;

        while(!robot.getLocation().equals(robot.getTargetLocation()) && active) {
              // wait for a while if we are supposed to
              if (delay > 0)
                  robot.sleep(delay);
              robot.setHeading(determineHeading());  /* Face the direction */
              robot.advance();
        }
    }


    // EXPLANATION: this method returns 1 if the target is north of the robot,
    // -1 if the target is south of the robot, or 0 if otherwise.
    // tested by using loppy generator and watched the out put of [System.out.println("Is north? : " + isTargetNorth());] which was placed just before robot.advance();
    public byte isTargetNorth() {
        if (robot.getLocation().y > robot.getTargetLocation().y)
          return 1;
        else if (robot.getLocation().y < robot.getTargetLocation().y)
          return -1;
        return 0;
    }


    // EXPLANATION: this method returns 1 if the target is east of the robot,
    // -1 if the target is west of the robot, or 0 if otherwise.
    public byte isTargetEast() {
      if (robot.getLocation().x < robot.getTargetLocation().x)
        return 1;
      else if (robot.getLocation().x > robot.getTargetLocation().x)
        return -1;
      return 0;
    }


    // EXPLANATION: this method causes the robot to look to the absolute direction
    // that is specified as argument and returns what sort of square there is
    /* [LONG VERSION] This method uses the shared addative rotation property between direction and relative direction. This is the same differance as AHEAD and where we want to look.
    The target absolute direction is some number of rotations from the current absolute direction. We know form the interface N=N+0, E=N+1, S=N+2 and W=N+3, so the differace can be found by taking the differance between the direction.
    Though the interface only specifies clockwise rotation so negetive rotations are converted to thier positive counterparts using mod 4. Added 4 first as java cant mod negetives.
    Tested with: [System.out.println("North:"+ lookHeading(IRobot.NORTH)+"EAST :" + lookHeading(IRobot.EAST)+" SOUTH:" + lookHeading(IRobot.SOUTH)+" WEST:" + lookHeading(IRobot.WEST));] and [System.out.println("rotationNum :" + rotationNum);]*/
    public int lookHeading(int absoluteDirection) {
        int rotationNum = (4 + absoluteDirection - robot.getHeading()) % 4;
        return robot.look(IRobot.AHEAD + rotationNum);
    }


    // EXPLANATION: this method determines the heading in which
    // the robot should head next to move closer to the target
    public int determineHeading() {
      int[] towards;
      int[] away;
      int rand;

      // makes 2 arrays for the possible directions. Towards target and not towards target. There are two possible states, both axis wrong (so 2 in each) or 1 axis right and the 3 wrong (1 and 3)
      if (isTargetEast() == 0 || isTargetNorth() == 0) {
        towards = new int[1];
        away = new int[3];
      } else {
        towards = new int[2];
        away = new int[2];
      }

        // Axis are tested and Split into 2 groups,Towards target or Away form target
        // Y axix: North and South
        switch (isTargetNorth()) {
        case 1:
            towards[0] = IRobot.NORTH;
            away[0] = IRobot.SOUTH;
            break;
        case -1:
            towards[0] = IRobot.SOUTH;
            away[0] = IRobot.NORTH;
            break;
        case 0:
            away[0] = IRobot.SOUTH;
            away[2] = IRobot.NORTH;
            break;
        }

        // X axis: East and West
        switch (isTargetEast()) {
        case 1:
            towards[1] = IRobot.EAST;
            away[1] = IRobot.WEST;
            break;
        case -1:
            towards[1] = IRobot.WEST;
            away[1] = IRobot.EAST;
            break;
        case 0:
            away[1] = IRobot.WEST;
            away[2] = IRobot.EAST;
            break;
        }

        rand = (int)Math.floor(Math.random() * 4);
        // choose random direction in TOWARDS FIRST if possible starts at random rand and loops round
        for (int i = 0 ; i < towards.length; i++) {
          int e = towards[(rand + i) % towards.length];
          // Test if element/direction points to wall and returns it if it doesnt
          if (lookHeading(e) != IRobot.WALL)
            return e;
        }

        // choose random direction in away NEXT starts at rand and loops round
        for (int i = 0 ; i < away.length; i++) {
          int e = away[(rand + i) % away.length];
          // Test if element/direction points to wall
          if (lookHeading(e) != IRobot.WALL)
            return e;
        }

        // Whats will be returned if all else fails. Impossible to reach this in normal use.
        return 0;
    }


    // this method returns a description of this controller
    public String getDescription() {
       return "A controller which homes in on the target";
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
    }

    // sets the reference to the robot
    public void setRobot(IRobot robot) {
       this.robot = robot;
    }
}
