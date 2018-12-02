import uk.ac.warwick.dcs.maze.logic.*;
import java.awt.Point;

// enum used as the 2 are constant and thier methods can be grouped together
public enum Directions {
        // Array of all directions
        All(true),
        // Array of all directions Excluding BEHIND
        Forwards(false);

        private int[] directionArray;

        // Creates Array with Directions
        Directions(boolean all) {
            if (all) {
                this.directionArray = new int[]{IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT, IRobot.BEHIND};
            } else {
                this.directionArray = new int[]{IRobot.AHEAD, IRobot.LEFT, IRobot.RIGHT};
            }
        }

        // Returns array of directions with its elements randomly shuffled around

        public int[] shuffled() {
            int[] array = this.directionArray;
            for ( int i=array.length-1 ; i>0 ; i-- ) {
                int r = (int)( Math.random() * array.length ); // r = random index in array
                int e = array[r]; // e = element in random array position 'r'
                array[r] = array[i]; // swap current element 'i' with random element at 'r'
                array[i] = e;
            }
            return array;
        }

        // Returns Ordered array of directions
        public int[] ordered() {
            return this.directionArray;
        }

    }
