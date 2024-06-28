
public class Obstacle {
    public String type;
    public int x,y = 0;
    public int[] coordinates = {x,y};
    public int width = 50;
    public int height = 50;
    public Obstacle(String t, int xcoor ){
        x = xcoor;
        type = t;
        coordinates[0] = x;
        coordinates[1] = y;

    }
    public int getX(){
        return x;
    }
    public void setX(int num){
        x=num;
        coordinates[0] = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int num){
        y=num;
        coordinates[1] = y;
    }
    public void moveDown(int num){
        y+= num;
        coordinates[1] = y;
    }
    public int[] getCoor(){
        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }
    public String getType(){
        return type;
    }
    public void setType(String t){
        type = t;
    }
    public String toString(){
        return type + " Obstacle at " + x +" "+y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}