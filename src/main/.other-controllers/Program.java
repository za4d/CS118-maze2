import uk.ac.warwick.dcs.maze.gui.MazeApp;
import uk.ac.warwick.dcs.maze.logic.MazeLogic;

public class Program {
    // do not allow this class to be instantiated
    private Program() {}

    // the main entry point for this program
    public static void main(String args[]) {
        // initialise the maze configuration and add an instance of
        // the random robot controller
		MazeLogic logic = new MazeLogic();
        runNormal();
    }

    private static void runNormal() {
      logic.getControllerPool().addController(new Info());
      logic.getControllerPool().addController(new RandomController());
      logic.getControllerPool().addController(new HomingController());
      logic.getControllerPool().addController(new Explorer());
      logic.getControllerPool().addController(new Explorer2());
      logic.getControllerPool().addController(new Explorer3());
      logic.getControllerPool().addController(new GrandFinale());

      // run the maze
      new MazeApp(logic);
    }

    private static void runTrainer() {
      
    }
}
