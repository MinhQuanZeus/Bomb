package models;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerModel extends GameModel {

    public static final int MAX_SPEED = 4;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 56;

    private int maxBomb;
    private int countBomb;
    private int explosionSize;
    private boolean explode;

    public PlayerModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        maxBomb = 1;
        explosionSize = 1;
        speed = 2;
        explode = false;
    }

    public void increaseCountBomb() {
        countBomb++;
    }

    public void reduceCountBomb() {
        countBomb--;
    }

    public boolean checkMaxBomb() {
        if (countBomb < maxBomb) {
            return true;
        }
        return false;
    }

    public int getExplosionSize() {
        return explosionSize;
    }
    public void expandExplosionSize(){
        explosionSize++;
    }

    public void expandMaxBomb(){
        maxBomb++;
    }

    public void speedUp() {
        if(speed<= MAX_SPEED)
        speed+=1;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public Rectangle getRect() {
        return super.getBottomRect(x, y);
    }

    public boolean isExplode() {
        return explode;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }
}
