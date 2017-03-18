package controllers.enemy_behavior.attack;

import controllers.EnemyController;
import controllers.enemy_behavior.move.MoveRandomStupid;
import controllers.enemy_weapon.BulletController;
import controllers.enemy_weapon.ShotDirection;
import gui.GameFrame;
import manager.ControllerManager;
import manager.GameManager;
import models.*;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class AttackShootABullet extends EnemyAttackBehavior {
    protected String moveDirection = "";
    /*
    giả là đã có đạn
    TO DO: thay băng đạn thật
    */
    private Vector<String> bullets;
    private int delayShoot = 1000;
    private long lastTimeShoot = System.currentTimeMillis();
    // số phát bắn
    private int shootNumber = 2;
    // số phát đã bắn
    private int shootedNumber = 0;

    private int countEnemyTryingToShoot = 0;
    private int enemyTryingToShootLimit = 4;

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        super.attack(model, view, playerModel,type, enemyController);

        int widthBullet = BulletModel.WIDTH;
        int heightBullet = BulletModel.HEIGHT;
        int xBullet = 0;
        int yBullet = 0;


        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeShoot >= delayShoot) {

            ShotDirection typeShoot = ShotDirection.DOWN;
            boolean shoot = true;
            switch (Utils.getRandom(4)) {
                case (0): { // len
                    xBullet = model.getX() + model.getWidth() / 2 - widthBullet / 2;
                    yBullet = model.getY() + 5;
                    moveDirection = "len";
                    switch (type) {
                        case FIRE_HEAD: {
                            typeShoot = ShotDirection.UP;
                            break;
                        }
                    }
                    break;
                }

                case (1): { // xuong
                    xBullet = model.getX() + model.getWidth() / 2 - widthBullet / 2;
                    yBullet = model.getY() + model.getHeight() - 5;
                    moveDirection = "xuong";

                    switch (type) {
                        case FIRE_HEAD: {
                            typeShoot = ShotDirection.DOWN;
                            break;
                        }
                    }
                    break;
                }
                case (2): { // trai
                    xBullet = model.getX() + 5;
                    yBullet = model.getY() + model.getHeight() - model.getHeight()/3 - heightBullet/2;
                    moveDirection = "trai";

                    switch (type) {
                        case FIRE_HEAD: {
                            typeShoot = ShotDirection.LEFT;
                            break;
                        }
                    }
                    break;
                }
                case (3): { // phai
                    xBullet = model.getX() + model.getWidth() - 5;
                    yBullet = model.getY() + model.getHeight() - model.getHeight()/3 - heightBullet/2;
                    moveDirection = "phai";

                    switch (type) {
                        case FIRE_HEAD: {
                            typeShoot = ShotDirection.RIGHT;
                            break;
                        }
                    }
                    break;
                }
            }

            Rectangle r = new Rectangle(xBullet + 5, yBullet + 10, BulletModel.WIDTH - 10, BulletModel.HEIGHT / 2);

            if(xBullet < 0  || xBullet > GameFrame.WIDTH - model.getWidth() || yBullet < 0  || yBullet > GameFrame.HEIGHT - model.getHeight() - 30){
                shoot = false;
                countEnemyTryingToShoot++;
            }else{
                for (int i = 0; i < GameManager.arrBlocks.size(); i++) {
                    if (GameManager.arrBlocks.get(i).getModel().getRect().intersects(r)) {

                        shoot = false;
                        countEnemyTryingToShoot++;
                        break;
                    }
                }
            }

            if (shoot) {
                lastTimeShoot = currentTime;
                shootedNumber++;
                BulletController bulletController = BulletController.create(xBullet, yBullet, typeShoot);
                GameManager.controllerManager.add(bulletController);
                GameManager.collisionManager.add(bulletController);
            }

            if (shootNumber == shootedNumber || countEnemyTryingToShoot == enemyTryingToShootLimit) {
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                enemyController.setEnemyMoveBehavior(new MoveRandomStupid());
            }

        }

        switch (type) {
            case FIRE_HEAD: {
                setImage();
            }
        }
    }

    public void setShootNumber(int shootNumber) {
        this.shootNumber = shootNumber;
    }

    public void setDelayShoot(int delayShoot) {
        this.delayShoot = delayShoot;
    }
}
