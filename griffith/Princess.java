package griffith;
import robocode.*;
import java.awt.Color;
import java.util.Random;

/**
 * Princess - a robot by (your name here)
 */
public class Princess extends Robot
{
    boolean movingForward = true;

    /**
     * run: Princess's default behavior
     */
    public void run() {
        setBodyColor(Color.pink);
        setBulletColor(Color.PINK);
        setRadarColor(Color.PINK);
        setScanColor(Color.pink);
        setGunColor(Color.RED);
        
        while (true) {
            wallFollow(); // Adjust the movement to follow walls
        }
    }           
    
    public void onScannedRobot(ScannedRobotEvent e) {
        
        double distance = e.getDistance();
        if (distance < 100) {
            fire(3); // If the enemy is close, fire with power 3
        } else if (distance < 200) {
            fire(2); // If the enemy is at medium range, fire with power 2
        } else {
            fire(1); // If the enemy is far away, fire with power 1
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        double bearing = e.getBearing();
        turnRight(90 - bearing); // Turn to face the robot that hit
        movingForward = false; // Change direction to move backward
        while (true) {
            scan(); // Scan for the robot that hit
            back(100); // Keep moving backward
        }
    }
    
    public void onHitByRobot(HitByRobotEvent e) {
        double bearing = e.getBearing();
        turnRight(90 - bearing); // Turn to face the robot that hit
        fire(3); // Fire back
        setBack(200); // Move away
        turnRight(45);
        turnLeft(45);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent event)
    {      
        // Move back a random distance between 150 and 250
        back(new Random().nextInt(100) + 150); 
        // Turn left or right randomly after hitting the wall
        if (new Random().nextBoolean()) {
            turnLeft(new Random().nextInt(180)); // Turn left with a random angle between 0 and 180 degrees
        } else {
            turnRight(new Random().nextInt(180)); // Turn right with a random angle between 0 and 180 degrees
        }
    }

    /**
     * wallFollow: Adjust the movement to follow walls
     */
    public void wallFollow() {
        // Check if close to wall
        if (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50) {
            // Move away from wall
            back(50);
            if (new Random().nextBoolean()) {
                turnLeft(90);
            } else {
                turnRight(90);
            }
        }
        ahead(100); // Move forward
    }
}