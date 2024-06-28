import javax.swing.JFrame;

public class FinalProject {
    private static JFrame f = new JFrame();
    private static SkiGUI s;
    private static Player a = new Player();
    private static GameData data = new GameData();

    //game loop that runs until the game is over (boolean is defined in GameData)
    public void gameLoop(Player p) {
        //continuously check if the game is over
        while (data.isGameOver(p) ==  false) {
            try {

            // Threads basically allow different tasks to run at the same time instead of one before the other.
            
            // this just pauses the current thread for 20 milliseconds (you can adjust to chagne speed)
                Thread.sleep(20);
            } catch (InterruptedException e) {

                // if there's an exception, then this thing just prints what went wrong -- for debugging
                e.printStackTrace();
            }
        }

        // display a game over message when the game is over, and stop the game
        s.writeMsg("Game Over!");
        s.stopGame();
    }

    public static void main(String[] args) {

        //initialize player with 3 lives
        a.setLives(3); 

        // create SkiGUI with player and game data, then create the JFrame for everything to kind of "lay" on 

        s = new SkiGUI(a, data); // initialize SkiGUI
        f.add(s); //Add SkiGUI
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 1000); //set up window
        s.drawLives(a); //set up heading (lives)
        s.printScore(a); //set up heading (score)
        s.writeMsg("Welcome to SkiSurfers"); //write a message at the start of the game

        //start the game loop (in a separate thread)
        //a thread lets the program do multiple tasks at the same time
        //here, a new 'Thread' object is created
        new Thread(() -> {
            FinalProject game = new FinalProject();
            game.gameLoop(a);
        }).start(); 
    }
}
