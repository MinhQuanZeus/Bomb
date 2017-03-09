package models;

/**
 * Created by QuanT on 3/9/2017.
 */
public class EnemyModel extends GameModel {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public int speed = 5;

    public boolean destroy = false;

    public EnemyModel(int x, int y, int width, int height,int speed) {
        super(x, y, width, height);

    }

    public EnemyModel(int x, int y,int speed) {
        super(x, y, WIDTH, HEIGHT);
        this.speed = speed;
    }

    public int getHang(){
        return y / HEIGHT;
    }

    public int getCot(){
        return x / WIDTH;
    }

    public void moveCorrectly(int x1,int y1){
        double dis = Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
        int vx = (int) ((x1 - x) * speed/dis);
        int vy = (int) ((y1 - y) * speed/dis);

        x += vx;
        y += vy;

        if(Math.abs(x-x1) < speed && Math.abs(y-y1) < speed){
            x = x1;
            y = y1;
        }
    }

    public void moveLeft(){
        GameVector v = new GameVector(-speed,0);
        super.move(v);
    }

    public void moveRight(){
        GameVector v = new GameVector(0,speed);
        super.move(v);
    }

    public void moveUp(){
        GameVector v = new GameVector(0,-speed);
        super.move(v);
    }

    public void moveDown(){
        GameVector v = new GameVector(0,+speed);
        super.move(v);
    }

    public void setDestroy(boolean destroy){
        this.destroy = destroy;
    }

    public boolean isDestroy(){
        return  destroy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
