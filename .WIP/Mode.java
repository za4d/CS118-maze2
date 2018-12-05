import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;
//TODO absorb into exploerer using inherited methods?
// Enum used as values are constants and expansion is limited
public enum Mode {
  Explore(true),
  BackTrack(false);

  // Is the robot exploring?
  private boolean exploring;

  // contructor sets isExploring boolean values

  Mode(boolean exploring) {
    this.exploring = exploring;
  }

  // Returns answer to is Mode Exploreing
  // mode.name() == "Explore"
  public boolean isExploring() {
    return this.exploring;
  }

  //TODO Remove?
  public String getMode() {
    return name();
  }

}
