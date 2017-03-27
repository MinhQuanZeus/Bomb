package models;

import controllers.enemy_weapon.ShotDirection;
import manager.GameManager;
import utils.Utils;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerModel extends GameModel {

    public static final int MAX_SPEED = 4;
    public static final int MIN_SPEED = 2;
    public static final int MAX_LIFE = 6;


    private int life;
    private int maxBomb;
    private int countBomb;
    private int explosionSize;
    private boolean explode;
    private int score;
    private boolean immunity;
    private int countDownImmunity;
    private int countDownSlow;
    private int numberShuriken;
    private int storeSpeed;
    private boolean kick = false;
    private boolean driver;
    private String pet;
    private ShotDirection shotDirection = ShotDirection.RIGHT;

    public PlayerModel(int x, int y) {
        super(x, y, 0, 0);
        countDownSlow = -1;
        maxBomb = 1;
        explosionSize = 1;
        speed = 2;
        storeSpeed = 2;
        numberShuriken = 0;
        explode = false;
        driver = false;
        if (GameManager.versus) {
            life = 0;
        } else {
            life = 3;
        }
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

    public void bonusLife() {
        if (life + 1 <= MAX_LIFE) {
            life++;
        }
    }

    public void expandExplosionSize() {
        explosionSize++;
    }

    public void expandExplosionSize(int size) {
        explosionSize += size;
    }

    public void expandMaxBomb() {
        maxBomb++;
    }

    public void speedUp() {
        if (speed < MAX_SPEED)
            speed++;
        if (storeSpeed < MAX_SPEED)
            storeSpeed++;
    }

    public void speedDown() {
        countDownSlow = 300;
        if (speed >= MIN_SPEED)
            speed--;
        if (storeSpeed >= MIN_SPEED)
            storeSpeed--;
    }

    public void getInFish() {
        storeSpeed = speed;
        speed = MAX_SPEED;
    }

    public void getOutFish() {
        speed = storeSpeed;
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
        if (!driver && explode) {
            maxBomb = 1;
            explosionSize = 1;
            speed = 2;
            numberShuriken = 0;
            Utils.playSound("player-out.wav", false);
        }
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

    public void checkSlow() {
        if (countDownSlow >= 0) {
            if (countDownSlow == 0) {
                speedUp();
            }
            countDownSlow--;
        }
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

    public void decreaseNumberShuriken() {
        numberShuriken--;
    }

    public void bonusShuriken() {
        if (numberShuriken + 3 <= 6) {
            numberShuriken += 3;
        } else if (numberShuriken + 3 > 6) {
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

    public int getMaxBomb() {
        return maxBomb;
    }

    public boolean isDriver() {
        return driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    @Override
    public void setWidth(int width) {
        if (this.width != 0)
            x -= (width - this.width) / 2;
        super.setWidth(width);
    }

    @Override
    public void setHeight(int height) {
        if (this.height != 0)
            y -= height - this.height;
        super.setHeight(height);
    }

    public String getPet() {
        return pet;
    }

    public int getCountDownSlow() {
        return countDownSlow;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }
}
