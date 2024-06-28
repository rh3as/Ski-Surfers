import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameData {
    static ArrayList<Obstacle> obstacles; // list that holds the 'living' obstacles
    private static int playerStatus = 0; // status of player (0 = no collision, 1 = good collision, 2 = bad collision)
    private Random rand = new Random(); // random object
    private static final int MIN_DISTANCE = 10; // Minimum distance between obstacles so that they don't spawn directly on top of each other

    // constructor
    GameData() {
        obstacles = new ArrayList<>(); //initialize obstacles list
        startObstacleTimer(); // start timer to place obstacles in aloop
    }

    // method to start a timer that places obstacles at specified intervals
    private void startObstacleTimer() {
        Timer obstacleTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //action performed isn't like a key event, it's just anything; it's constanly happening
                placeObstaclesFairly(); // places obstacles at random
            }
        });
        obstacleTimer.start(); // starts the timer!!
    }

    // method that places obstacles in the obstacle arraylist at random x coordinates (they are fair because they can't be placed directly on top of each other)
    public void placeObstaclesFairly() {
        int r1 = rand.nextInt(100); // Randomize if it's good/bad
        int r2 = rand.nextInt(551); // Randomize x coordinate (assuming the panel width is 600 and obstacle width is 50)
        String type;
        if (r1 < 69) { // make about 70 percent of obstacles bad and the other 30 percent good obstacles
            type = "bad";
        } else {
            type = "good";
        }

        boolean canPlace = true; // create a boolean to make sure they aren't placed on top of each other
        for (Obstacle obstacle : obstacles) {
            if (Math.abs(obstacle.getX() - r2) < MIN_DISTANCE) {
                canPlace = false;
                break;
            }
        }

        if (canPlace) {
            obstacles.add(new Obstacle(type, r2)); // when conditions are satisfied, a new obstacle is made and added to the obstacles arraylist
        }
    }

    // method to decide the status of the player (status of player (0 = no collision, 1 = good collision, 2 = bad collision)
    // returns player status
    public static int decideStatus(Player p) {
        int playerX = p.getX();
        int playerY = p.getY();
        int playerWidth = p.getWidth();
        int playerHeight = p.getHeight();

        //iterate through obstacles to check for collisions

        ListIterator<Obstacle> iterator = obstacles.listIterator(); // this is how you have to iterate through an arraylist :/
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next(); //the next one in the arraylist
            int obstacleX = obstacle.getX();
            int obstacleY = obstacle.getY();
            int obstacleWidth = obstacle.getWidth();
            int obstacleHeight = obstacle.getHeight();

            //check for collision (if one of the top corners of the player (left or right) falls inside the square of the obstacle)
            boolean collision = playerX < obstacleX + obstacleWidth &&
                                playerX + playerWidth > obstacleX &&
                                playerY < obstacleY + obstacleHeight &&
                                playerY + playerHeight > obstacleY;

            if (collision) {
                //update playerstatus based on type of collision (good or bad)
                if (obstacle.getType().equals("good")) {
                    playerStatus = 1;
                } else {
                    playerStatus = 2;
                }
                iterator.remove(); // remove the obstacle while iterating thru
                return playerStatus; // return player status
            }
        }
        return 0; // player status is 0, meaning no collision
    }

    // method that updates the player's score when the player has dodged obstacles
    public void updateScore(Player p) {
        // Check if obstacles have exited the screen without colliding with the player
        ListIterator<Obstacle> iterator = obstacles.listIterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            if (obstacle.getY() > 1000) { // Assuming the panel height is 1000
                iterator.remove(); // Remove obstacle using iterator's remove method
                if (obstacle.getType().equals("bad")) { // Only increase score for "bad" obstacles
                    p.score++; // Increase score by one
                }
            }
        }
    }
    

    public void updateLives(Player p) {
        int collisionStatus = decideStatus(p); // Check collision status

        if (collisionStatus == 2) { // If collided with a bad obstacle
            p.lives--; // Decrease lives by one
        } else if (collisionStatus == 1) {
            p.lives++;
        }

        if (p.getLives() <= 0) {
            p.lives = 0;
        } else if (p.getLives() >= 3) {
            p.lives = 3;
        }
    }

    public boolean isGameOver(Player p) {
        return p.getLives() <= 0;
    }
    
}
