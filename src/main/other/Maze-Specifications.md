# MAZE 2

## Task 2.1.2
 - modify run to store result in Exits

## Task
 - Extend it so it distributes control accoding to Exits
 - These methods should return a direction which run will take



# MAZE 1

## Task 1.1.2
 - moves randomly
 - but doesnt crash into walls

## Task 1.1.3
 - Only change if collision ahead
 - Randomly choose direction out of the 3
 - Equal probabilty of all directions
 - Log the robots movements
   - choose new direction every 8 steps roughly

## Task 1.1.5
 - Selects heading towards target if it can move in that direction (Good choice)
 - No collisions
 - If multiple Good choices available choose randomly
 - If no Good choices choose randomly between rest


 # JUNIT Help
 [HELP](http://junit.sourceforge.net/javadoc/org/junit/Assert.html#assertTrue(boolean))

 ```java
 /*
     Tests whether the homing controller's isTargetNorth
     method works as specified.
 */



 @Test(timeout=10000) // Set timeout
 public void isTargetNorthTest() { // name test
     // [DESRIPTION OF TEST]
     for(int i=0; i<this.columns; i++) { // range of states to test
         this.robot.setTargetLocation(new Point(i,0));

         assertTrue(
             "[Message if assertion FAILs]",
             /*[CONTION == TO TEST]*/);
     }






 @Test(timeout=10000)
 public void isTargetNorthTest() {
     // move the target to some cells north of the robot and
     // test whether isTargetNorth correctly identifies this
     for(int i=0; i<this.columns; i++) {
         this.robot.setTargetLocation(new Point(i,0));

         assertTrue(
             "HomingController doesn't think the target is north!",
             this.controller.isTargetNorth() == 1);
     }

     // move the target to some cells south of the robot and
     // test whether isTargetNorth correctly identifies this
     for(int i=0; i<this.columns; i++) {
         this.robot.setTargetLocation(new Point(i,4));

         assertTrue(
             "HomingController doesn't think the target is south!",
             this.controller.isTargetNorth() == -1);
     }

     // move the target to some cells on the same y-level as the
     // robot and test whether isTargetNorth correctly identifies this
     for(int i=0; i<this.columns; i++) {
         this.robot.setTargetLocation(new Point(i,2));

         assertTrue(
             "HomingController doesn't think the target is on the same level!",
             this.controller.isTargetNorth() == 0);
     }
 }```
