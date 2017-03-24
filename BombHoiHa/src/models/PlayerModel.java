package models;

import controllers.enemy_weapon.ShotDirection;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerModel extends GameModel {

    public static final int MAX_SPEED = 4;
    public static final int MIN_SPEED = 2;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 56;
    public static final int MAX_LIFE = 6;


    private int life;
    private int maxBomb;
    private int countBomb;
    private int explosionSize;
    private boolean explode;
    private int score;
    private boolean immunity;
    private int countDownImmunity;
    private int numberShuriken=0;
    private boolean kick = false;
    private ShotDirection shotDirection = ShotDirection.RIGHT;



    public PlayerModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        maxBomb = 1;
        explosionSize = 1;
        speed = 2;
        explode = false;
        life = 3;
        countDownImmunity = 100;
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
    public void bonusLife(){
        if(life+1<=MAX_LIFE){
            life++;
        }
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

    public void speedDown() {
        if(speed>= MIN_SPEED)
            speed-=1;
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
        numberShuriken = 0;
        speed = 2;
    }

    public void increaseScore() {
        score += 100;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public void reduceLife() {
        this.life--;
    }

    public boolean isImmunity() {
        return immunity;
    }

    public void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }

    public void checkImmunity() {
        if (immunity) {
            countDownImmunity--;
            if (countDownImmunity == 0) {
                immunity = false;
                countDownImmunity = 100;
            }
        }
    }
    public int getNumberShuriken() {
        return numberShuriken;
    }
    public void decreaseNumberShuriken(){
        numberShuriken--;
    }
    public void bonusShuriken(){
        if(numberShuriken+3<=6) {
            numberShuriken += 3;
        }else if(numberShuriken+3>6){
            numberShuriken = 6;
        }
    }

    public boolean isKick() {
        return kick;
    }

    public void setKick(boolean kick) {
        this.kick = kick;
    }
    public ShotDirection getShotDirection() {
        return shotDirection;
    }

    public void setShotDirection(ShotDirection shotDirection) {
        this.shotDirection = shotDirection;
    }
}
