## CONTANTS

# Direction Integers
- IRobot.NORTH
- IRobot.EAST
- IRobot.SOUTH
- IRobot.WEST

NORTH+1=EAST, EAST+1=SOUTH, SOUTH+1=WEST

# Relative Direction (r_direction) Integers
- IRobot.LEFT
- IRobot.RIGHT
- IRobot.AHEAD
- IRobot.BEHIND
- IRobot.CENTRE ~ Do Nothing

Same addition rule from above applies

# robot.look wall states
- IRobot.WALL ~ WALL
- IRobot.PASSAGE ~ Empty space
- IRobot.BEENBEFORE ~ Like passege but been there

## COMMANDS

*robot.look(r_direction)* ~ Returns wall type in given r_Direction

*robot.getHeading()* ~ Returns robots Direction
*robot setHeading(direction)* ~ Makes robot face N/E/S/W

*robot.getLocation()* ~ Returns object of point type with members x and y
*robot.getTargetLocation()* ~ Same as above but for the target

*robot.face(r_direction)* ~ Makes the robot rotate in r_Direction

*robot.advance()* ~ Moves AHEAD in current headings direction

*robot.getRuns()* ~ Returns integer with the number of attempts at a maze

*robot.getLogger().log(r_direction)* ~ Adds 1 to its r_direction count
