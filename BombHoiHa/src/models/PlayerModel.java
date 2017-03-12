package models;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerModel extends GameModel {
    public static final int MAXSPEED = 5;
    private int speed = 2;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 70;

    private int maxBomb;
    private int countBomb;
    private int explosionSize;

    public PlayerModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        maxBomb = 1;
        explosionSize = 2;
    }

    public void increaseCountBomb() {
        countBomb++;
    }

    public void reduceCountBomb() {
        countBomb--;
    }

    public boolean checkMaxBomb() {
     //   System.out.println("max bomb: "+maxBomb);
        if (countBomb < maxBomb) {
            return true;
        }
        return false;
    }

    public int getExplosionSize() {
        return explosionSize;
    }
    public void expandExplosionSize(){
        explosionSize+=2;
    }

    public void expandMaxBomb(){
        maxBomb++;
        System.out.println("max bomb: "+maxBomb);
    }

    public void speedUp() {
        if(speed<=MAXSPEED)
        speed+=1;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCountBomb() {
        return countBomb;
    }
}
