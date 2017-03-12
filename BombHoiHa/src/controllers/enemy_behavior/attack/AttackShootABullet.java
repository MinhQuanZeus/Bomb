package controllers.enemy_behavior.attack;

import controllers.EnemyController;
import controllers.GameController;
import controllers.enemy_behavior.move.MoveRandomStupid;
import controllers.enemy_weapon.BulletController;
import controllers.enemy_weapon.ShotDirection;
import manager.ControllerManager;
import models.*;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.List;
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
    private int delayShoot = 2000;
    private long lastTimeShoot = System.currentTimeMillis();
    // số phát bắn
    private int shootNumber = 2;
    // số phát đã bắn
    private int shootedNumber = 0;

    private int countEnemyTryingToShoot = 0;
    private int enemyTryingToShootLimit = 4;

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, List<GameController> gameControllers, EnemyController.EnemyType type, EnemyController enemyController, ControllerManager controllerManager) {
        super.attack(model, view, playerModel, gameControllers, type, enemyController, controllerManager);

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
                    yBullet = model.getY() + model.getHeight() - ItemMapModel.SIZE_TILED / 2 + heightBullet/2;
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
                    yBullet = model.getY() + model.getHeight();
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
                    xBullet = model.getX() + 5 - widthBullet;
                    yBullet = model.getY() + model.getHeight() - ItemMapModel.SIZE_TILED / 2 / 2 - heightBullet / 2;
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
                    xBullet = model.getX() + model.getWidth() - 5 + widthBullet;
                    yBullet = model.getY() + model.getHeight() - ItemMapModel.SIZE_TILED / 2 / 2 - heightBullet / 2;
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

            Rectangle r = new Rectangle(xBullet, yBullet, BulletModel.WIDTH, BulletModel.HEIGHT);

            for (int i = 0; i < gameControllers.size(); i++) {
                if (gameControllers.get(i).getModel().getRect().intersects(r)) {

                    shoot = false;
                    countEnemyTryingToShoot++;
                    break;
                }
            }

            if (shoot) {
                lastTimeShoot = currentTime;
                shootedNumber++;
                controllerManager.add(BulletController.create(xBullet, yBullet, typeShoot));
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
