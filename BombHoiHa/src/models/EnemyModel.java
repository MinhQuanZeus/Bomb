package models;

import controllers.EnemyController;
import manager.GameManager;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class EnemyModel extends GameModel implements Collision{
    public static final int WIDTH = 40;
    public static final int HEIGHT = 56;
    public static int enemyCount;
    private int hp = 1;
    private boolean destroy = false;

    private EnemyController.EnemyType type;
    public EnemyModel(int x, int y, int width, int height, int speed, int hp, EnemyController.EnemyType type) {
        super(x, y, width, height);
        this.speed = speed;
        this.type = type;
        enemyCount++;
    }

    public EnemyModel(int x, int y, int speed, int hp, EnemyController.EnemyType type) {
        super(x, y, WIDTH, HEIGHT);
        this.speed = speed;
        this.hp = hp;
        this.type = type;
        enemyCount++;
    }

    @Override
    public Rectangle getRect() {
        return super.getBottomRect(x, y);
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

    public EnemyController.EnemyType getType() {
        return type;
    }



    @Override
    public GameModel getModel() {
        return this;
    }

    @Override
    public void onContact(Collision other) {
    }
}
