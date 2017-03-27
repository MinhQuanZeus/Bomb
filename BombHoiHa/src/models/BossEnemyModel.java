package models;

import controllers.BossEnemyController;
import controllers.EnemyController;
import manager.GameManager;

import java.awt.*;

/**
 * Created by l on 3/24/2017.
 */
public class BossEnemyModel extends GameModel {
    public static final int BOSS_WIDTH = 150;
    public static final int BOSS_HEIGHT = 150;

    public static final int BOSS_SHADOW_DISTANCE = 10;

    public static final int BOSS_BIGHEAD_DEFAULT_HP = 8;
    public static final int BOSS_BIGHEAD_MAD_HP = 7;


    private boolean immunity = false;

    private int hp = 1;
    private boolean destroy = false;

    private BossEnemyController.BossType type;

    private int moveLike = 0;

    public BossEnemyModel(int x, int y,int speed,BossEnemyController.BossType type) {
        super(x, y, BOSS_WIDTH, BOSS_HEIGHT);
        this.speed = speed;
        this.hp = BOSS_BIGHEAD_DEFAULT_HP;
        this.type = type;
    }

    public void setImmunity(boolean immunity) {
        this.immunity = immunity;
    }

    public boolean isImmunity() {
        return immunity;
    }
    @Override
    public Rectangle getRect() {
        return new Rectangle(x + 70, y + height/2, width - 140, height/2 + BOSS_SHADOW_DISTANCE + BOSS_HEIGHT/5);
    }

    public Rectangle getRectOfBossAndShadow(){
        return new Rectangle(x,y,BOSS_WIDTH,BOSS_HEIGHT + BOSS_SHADOW_DISTANCE + BOSS_HEIGHT/5 - 5);
    }

    public void moveCorrectly(int x1, int y1){
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
        super.move(v, GameManager.arrBlocks);
    }

    public void moveRight(){
        GameVector v = new GameVector(speed,0);
        super.move(v,GameManager.arrBlocks);
    }

    public void moveUp(){
        GameVector v = new GameVector(0,-speed);
        super.move(v,GameManager.arrBlocks);
    }

    public void moveDown(){
        GameVector v = new GameVector(0,+speed);
        super.move(v,GameManager.arrBlocks);
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public BossEnemyController.BossType getType() {
        return type;
    }

    public void increaseMoveLike(){
        moveLike += 1;
    }

    public int getMoveLike() {
        return moveLike;
    }
}
