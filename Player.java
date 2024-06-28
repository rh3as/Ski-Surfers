public class Player {
    public int score = 0;
    public int lives = 3;
    public int x  = 300;
    public int y = 700;
    public int width = 40; 
    public int height = 40; 
    public Player(){ 

    }
    public int getScore(){
        return score;
    }
    public void addScore(int num){
        score += num;
    }
    public int getLives(){
        return lives;
    }
    public void setLives(int num){
        lives = num;
    }
    public int getX(){
        return x;
    }
    public void setX(int num){
            x = num;
    }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String toString(){
        return "Score:"+ score + " Lives: " + lives;
    }
}
