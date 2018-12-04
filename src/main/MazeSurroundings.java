import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

//TODO[ !!! ] Fix probabilities

// enum used as the 2 are constant and thier methods can be grouped together
public class MazeSurroundings {
    // the robot in the maze
    private IRobot robot;
    // Array of Directions
    private int[] directionArray;


    // initialse Array of Directions
    MazeSurroundings(IRobot robot, int[] directionArray) {
        this.robot = robot;
        this.directionArray = directionArray;
    }



    // returns a randomly chosen direction thats a `squareType` (`squareType` = Passage, BeenBefore or Wall)
    // (Passage used as example)
    public int nextRandom(int squareType) {
        // shuffle directions and search for 'Passages'
        for (int dir : shuffle(this.directionArray) ) {
            // If 'Passage' found...
            if (robot.look(dir) == squareType) {
                // return 'Passage' direction
                return dir;
            }
        }
        // else if none found
        return -1;
        // return IRobot.Centre;
        //TODO[ ! ] Replace -1's with IRobot.Centre
    }


    // returns the number of surrounding square that are `squareType` (`squareType` = Passage, BeenBefore or Wall)
    // (Passage used as example)
    public int count(int squareType) {
        int num = 0;
        // increment when 'Passage' found
        for (int dir : this.directionArray ) {
            if (robot.look(dir) == squareType) num++;
        }
        // return `Passage` count
        return num;
    }


    // tests wether there's a `squareType` square around [`squareType` = Passage, BeenBefore or Wall]
    // (Passage used as example)
    public boolean isThereA(int squareType) {
        // Look around
        for (int dir : this.directionArray ) {
            // If 'Passage' found return True
            if (robot.look(dir) == squareType) return true;;
        }
        // Else return false
        return false;
    }




    // EXIT = NOT WALL


    // returns a randomly chosen direction thats a Not a Wall
    public int nextRandomExit() {
        // shuffle directions and look around
        for (int dir : shuffle(this.directionArray) ) {
            // If direction is not a wall...
            if (robot.look(dir) != IRobot.WALL) {
                // return direction
                return dir;
            }
        }
        // else if none found
        return -1;
        // return IRobot.Centre;
        //TODO[ ! ] Replace -1's with IRobot.Centre
    }


    // returns the number of surrounding square that are Not a Walls
    public int countExits() {
        int exits = 0;
        // increment when direction isnt a Wall
        for (int dir : this.directionArray ) {
            if (robot.look(dir) != IRobot.WALL) exits++;
        }
        // return NonWall count
        return exits;
    }


    // tests wether there's a NonWall square around you
    public boolean isThereAExit() {
        // Look around
        for (int dir : this.directionArray ) {
            // If NonWall found return True
            if (robot.look(dir) != IRobot.WALL) return true;;
        }
        // Else return false
        return false;
    }

    // Returns array of directions with its elements randomly shuffled around
    public static int[] shuffle(int[] array) {
        for ( int i=array.length-1 ; i>0 ; i-- ) {
            int r = (int)( Math.random() * array.length ); // r = random index in array
            int e = array[r]; // e = element in random array position 'r'
            array[r] = array[i]; // swap current element 'i' with random element at 'r'
            array[i] = e;
        }
        return array;

    }


    // Returns Ordered array of directions
    public int[] getDirections() {
        return this.directionArray;
    }

}
