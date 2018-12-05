
> Task :run


RobotImpl:

There are 28 methods in the ROBOT class...

public void uk.ac.warwick.dcs.maze.logic.RobotImpl.setMaze(uk.ac.warwick.dcs.maze.logic.Maze)
public int uk.ac.warwick.dcs.maze.logic.RobotImpl.getRuns()
public int uk.ac.warwick.dcs.maze.logic.RobotImpl.look(int) throws java.lang.RuntimeException
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.face(int) throws java.lang.RuntimeException
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.setHeading(int) throws java.lang.RuntimeException
public int uk.ac.warwick.dcs.maze.logic.RobotImpl.getHeading()
public int uk.ac.warwick.dcs.maze.logic.RobotImpl.getLocationX()
public int uk.ac.warwick.dcs.maze.logic.RobotImpl.getLocationY()
public java.awt.Point uk.ac.warwick.dcs.maze.logic.RobotImpl.getTargetLocation()
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.setLocation(java.awt.Point)
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.setTargetLocation(java.awt.Point)
public long uk.ac.warwick.dcs.maze.logic.RobotImpl.getSteps()
public long uk.ac.warwick.dcs.maze.logic.RobotImpl.getCollisions()
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.notify(uk.ac.warwick.dcs.maze.logic.IEvent)
public java.awt.Point uk.ac.warwick.dcs.maze.logic.RobotImpl.getLocation()
public uk.ac.warwick.dcs.maze.logic.MovementLogger uk.ac.warwick.dcs.maze.logic.RobotImpl.getLogger()
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.sleep(int)
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.reset()
public void uk.ac.warwick.dcs.maze.logic.RobotImpl.advance() throws java.lang.RuntimeException
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()


Maze:

There are 20 methods in the MAZE class...

public void uk.ac.warwick.dcs.maze.logic.Maze.setStart(int,int)
public void uk.ac.warwick.dcs.maze.logic.Maze.setFinish(int,int)
public void uk.ac.warwick.dcs.maze.logic.Maze.setCellType(int,int,int)
public java.awt.Point uk.ac.warwick.dcs.maze.logic.Maze.getStart()
public java.awt.Point uk.ac.warwick.dcs.maze.logic.Maze.getFinish()
public int uk.ac.warwick.dcs.maze.logic.Maze.getWidth()
public int uk.ac.warwick.dcs.maze.logic.Maze.getHeight()
public int uk.ac.warwick.dcs.maze.logic.Maze.getCellType(java.awt.Point)
public int uk.ac.warwick.dcs.maze.logic.Maze.getCellType(int,int)
public void uk.ac.warwick.dcs.maze.logic.Maze.writeToFile(java.io.File,uk.ac.warwick.dcs.maze.logic.IRobot) throws java.lang.Exception
public void uk.ac.warwick.dcs.maze.logic.Maze.toggleCellType(int,int)
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()


MazeApp:

There are 11 methods in the MAZEAPP class...

public static void uk.ac.warwick.dcs.maze.gui.MazeApp.printDebug(java.lang.Object,java.lang.String)
public void uk.ac.warwick.dcs.maze.gui.MazeApp.notify(uk.ac.warwick.dcs.maze.logic.IEvent)
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()


MazeLogic:

There are 23 methods in the MAZELOGIC class...

public uk.ac.warwick.dcs.maze.logic.ControllerPool uk.ac.warwick.dcs.maze.logic.MazeLogic.getControllerPool()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.setMaze(uk.ac.warwick.dcs.maze.logic.Maze)
public uk.ac.warwick.dcs.maze.logic.Maze uk.ac.warwick.dcs.maze.logic.MazeLogic.getMaze()
public uk.ac.warwick.dcs.maze.logic.IRobot uk.ac.warwick.dcs.maze.logic.MazeLogic.getRobot()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.startController()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.resetController()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.addMazeGenerator(uk.ac.warwick.dcs.maze.logic.IMazeGenerator)
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.removeMazeGenerator(uk.ac.warwick.dcs.maze.logic.IMazeGenerator)
public uk.ac.warwick.dcs.maze.logic.IMazeGenerator[] uk.ac.warwick.dcs.maze.logic.MazeLogic.getMazeGenerators()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.setCurrentGenerator(uk.ac.warwick.dcs.maze.logic.IMazeGenerator)
public uk.ac.warwick.dcs.maze.logic.IMazeGenerator uk.ac.warwick.dcs.maze.logic.MazeLogic.getCurrentGenerator()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.setCurrentController(uk.ac.warwick.dcs.maze.logic.IRobotController)
public uk.ac.warwick.dcs.maze.logic.IRobotController uk.ac.warwick.dcs.maze.logic.MazeLogic.getCurrentController()
public void uk.ac.warwick.dcs.maze.logic.MazeLogic.notify(uk.ac.warwick.dcs.maze.logic.IEvent)
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()


PrimGenerator:

There are 12 methods in the PRIMGENERATOR class...

public java.lang.String uk.ac.warwick.dcs.maze.generators.PrimGenerator.getDescription()
public uk.ac.warwick.dcs.maze.logic.Maze uk.ac.warwick.dcs.maze.generators.PrimGenerator.generateMaze()
public javax.swing.JPanel uk.ac.warwick.dcs.maze.generators.PrimGenerator.getConfigurator()
public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
public final void java.lang.Object.wait() throws java.lang.InterruptedException
public boolean java.lang.Object.equals(java.lang.Object)
public java.lang.String java.lang.Object.toString()
public native int java.lang.Object.hashCode()
public final native java.lang.Class java.lang.Object.getClass()
public final native void java.lang.Object.notify()
public final native void java.lang.Object.notifyAll()


constants


Headings:
IRobot.NORTH -- 1000
IRobot.EAST -- 1001
IRobot.SOUTH -- 1002
IRobot.WEST -- 1003

Directions:
IRobot.AHEAD -- 2000
IRobot.RIGHT -- 2001
IRobot.BEHIND -- 2002
IRobot.LEFT -- 2003
IRobot.CENTRE -- 2004

Tiles:
IRobot.WALL -- 3000
IRobot.PASSAGE -- 3001
IRobot.BEENBEFORE -- 4000


Done!

