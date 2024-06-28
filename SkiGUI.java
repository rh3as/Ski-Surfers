import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

    public class SkiGUI extends JPanel implements ActionListener, KeyListener {
        Timer t = new Timer(3, this);
        int x = 300;
        double velx = 0;
        private JPanel headerPanel; //panel for the header at the top
        private JLabel livesLabel; //live count at top left
        private JLabel scoreLabel;//score at top right
        private JPanel msgBox;
        private JLabel msgLabel;
        private Player player;
        private GameData gameData;
        public boolean gameRunning = true;


    public SkiGUI(Player player, GameData gameData) {
        this.player = player;
        this.gameData = gameData;

        setLayout(new BorderLayout());
        
        Color lightBlue = new Color(173, 216, 230);
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(600, 100));

        livesLabel = new JLabel();
        livesLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 40));

        headerPanel.add(livesLabel, BorderLayout.WEST); //add livesLabel to the panel
        headerPanel.add(scoreLabel, BorderLayout.EAST); // Add scoreLabel to the panel
        headerPanel.add(Box.createHorizontalGlue()); // Add glue for flexible spacing
        headerPanel.setBackground(lightBlue);
        
        msgBox = new JPanel();
        msgBox.setPreferredSize(new Dimension(600, 100));
        msgBox.setBackground(lightBlue);
        msgLabel = new JLabel(); // Example message
        msgLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        msgBox.add(msgLabel);
        
        add(headerPanel, BorderLayout.PAGE_START);
        add(msgBox, BorderLayout.PAGE_END);

        t.start(); //starts timer
        addKeyListener(this); //add this keylistener to this jpanel
        setFocusable(true); //sets the focus to this which allows us to use the keylistener
        setFocusTraversalKeysEnabled(false); //tab keys etc, that act weird, makes them act normal

    }

    public void drawObstacles(Graphics g) {
        for (Obstacle obstacle : GameData.obstacles) {
            if (obstacle.getType().equals("good")) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(obstacle.getX(), obstacle.getY(), 50, 50); // Drawing obstacles as rectangles for now
        }
    }
    public void drawPlayer(Graphics g){
        g.fillRect(x, 700, 40, 40);
    }
    
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        //draw player
        drawPlayer(g);

        //draw obstacles
        drawObstacles(g);

    }

    public void actionPerformed(ActionEvent e) 
    {
        x += velx;

        // Ensure x is within the window (0 - 600)
        if (x < 0) 
        {
            x = 0;
        } 
        else if (x > getWidth() - 40) //player's width = 40
        { 
            x = getWidth() - 40;
        }

        //Update player coordinates
        player.setX(x);

        // Update obstacle positions
        for (Obstacle obstacle : GameData.obstacles) {
            obstacle.moveDown(1); // Move obstacles down by 1 unit
        }

        // Update score for obstacles that have exited the screen
        gameData.updateScore(player);

        // Check for collisions and update lives
        gameData.updateLives(player);


        // Update labels
        drawLives(player);
        printScore(player);

        if (gameData.isGameOver(player)) {
            gameRunning = false;
            writeMsg("Game Over!");
        }
        
        // Repaint the screen
        repaint();
    }

    public void left() {
        velx = -1.5;
    }
    public void right() {
        velx = 2;
    }
    public void keyPressed(KeyEvent e) {
        int code =  e.getKeyCode(); //each key has a key code which needs to be stored and checked
        if (code == KeyEvent.VK_LEFT) { //VK_a would be "a", etc.
            left();
            
        }
        if (code == KeyEvent.VK_RIGHT) { //VK_a would be "a", etc.
            right();
            
        }
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e){}

    public void drawLives(Player p){
        
        livesLabel.setText(" Lives: " +p.lives);

    }
    public void printScore(Player p){
        scoreLabel.setText("Score: "+p.getScore()+" ");
    }
    public void writeMsg(String s){
        msgLabel.setText(s);
    }

    public void stopGame() {
        gameRunning = false;
        t.stop();
    }

}

    


