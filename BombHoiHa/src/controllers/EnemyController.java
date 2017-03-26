package controllers;

import controllers.enemy_behavior.attack.AttackNothing;
import controllers.enemy_behavior.attack.EnemyAttackBehavior;
import controllers.enemy_behavior.destroy.DestroyNormal;
import controllers.enemy_behavior.destroy.EnemyBeingDestroyBehavior;
import controllers.enemy_behavior.move.*;
import manager.ControllerManager;
import manager.GameManager;
import models.*;
import utils.Utils;
import views.AutoLoadPic;
import views.EnemyView;
import views.GameView;

import java.awt.*;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */

// dùng hàm create trong enemycontroller: cần vị trí x,y, tốc độ   tuy nhiên máu là mặc định
public class EnemyController extends GameController implements Collision {
    protected PlayerModel playerModel;
    protected EnemyType type;
    private Stage enemyState;
    private FreezeBehavior freezeBehavior;

    protected EnemyMoveBehavior enemyMoveBehavior;
    protected EnemyBeingDestroyBehavior enemyBeingDestroyBehavior;
    protected EnemyAttackBehavior enemyAttackBehavior;

    // cần enum type để vẽ trong movebehavior, cần managerController để thêm việc tấn công, quái bắn lửa
    public EnemyController(int x, int y, int speed, int hp, EnemyView enemyView, PlayerModel playerModel, FreezeBehavior freezeBehavior, EnemyType type) {
        this(new EnemyModel(x, y, speed, hp, type), enemyView, playerModel, freezeBehavior, type);
        enemyState = Stage.NORMAL;
    }


    public EnemyController(GameModel model, GameView view, PlayerModel playerModel, FreezeBehavior freezeBehavior, EnemyType type) {
        super(model, view);
        this.playerModel = playerModel;
        this.type = type;
        this.freezeBehavior = freezeBehavior;
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
    }

    @Override
    public void run() {
        switch (this.enemyState) {
            case NORMAL:
                if (model instanceof EnemyModel) {
                    if (((EnemyModel) model).getHp() == 0) {
                        ((EnemyModel) model).setDestroy(true);
                    }
                    if (((EnemyModel) model).isDestroy()) {
                        if (model.isAlive()) {
                            enemyBeingDestroyBehavior.destroy((EnemyModel) model, (EnemyView) view, type);
                        } else {

                        }
                    } else {
                        enemyMoveBehavior.move((EnemyModel) model, (EnemyView) view, playerModel, type, this);
                        enemyAttackBehavior.attack((EnemyModel) model, (EnemyView) view, playerModel, type, this);
                    }
                }
                break;
            case FREEZE:
                break;
        }
        if (freezeBehavior != null) {
            freezeBehavior.run(this);
        }

    }

    public enum EnemyType {
        DUCK,
        SLIM_JELLY_HEAD,
        FIRE_HEAD,
        SMART_MAN
    }

    public Stage getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(Stage enemyState) {
        this.enemyState = enemyState;
    }

    //=========CREATE ENEMY
    public static EnemyController create(EnemyType type, int x, int y, PlayerModel playerModel) {
        EnemyController enemyController = null;
        // playerModel để truy đuổi nếu cần, gamemodels để check có đi đc ko,
        // type để có thể thực hiện việc vẽ hình di chuyển setImage trong MoveBehavior
        switch (type) {
            case DUCK: {
                enemyController = new EnemyController(x, y, 2, 1, new EnemyView(AutoLoadPic.enemy_Duck_Image_ImageMap.get("xuong0")), playerModel, new FreezeBehavior(200), type);
                enemyController.setEnemyMoveBehavior(new MoveRandomStupid());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                break;
            }
            case SLIM_JELLY_HEAD: {
                enemyController = new EnemyController(x, y, 2, 1, new EnemyView(AutoLoadPic.enemy_SlimJellyHead_ImageMap.get("xuong0")), playerModel, new FreezeBehavior(200), type);
                enemyController.setEnemyMoveBehavior(new MoveRandom_And_Jump());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                break;
            }
            case FIRE_HEAD: {
                enemyController = new EnemyController(x, y, 2, 1, new EnemyView(AutoLoadPic.enemy_fireHead_ImageMap.get("xuong0")), playerModel, new FreezeBehavior(200), type);
                enemyController.setEnemyMoveBehavior(new MoveRandomStupid());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                break;
            }
            case SMART_MAN: {
                enemyController = new EnemyController(x, y, 1, 1, new EnemyView(AutoLoadPic.enemy_smartMan_ImageMap.get("xuong0")), playerModel, new FreezeBehavior(200), type);
                enemyController.setEnemyMoveBehavior(new MoveFindPlayer());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                break;
            }
        }

        return enemyController;
    }

    public static EnemyController createByRow_Colum_Number(int typeNumber, int row, int colum, PlayerModel playerModel) {
        EnemyType type = null;
        switch (typeNumber) {
            case (20): {
                type = EnemyType.DUCK;
                break;
            }
            case (21): {
                type = EnemyType.SLIM_JELLY_HEAD;
                break;
            }
            case (22): {
                type = EnemyType.FIRE_HEAD;
                break;
            }
            case (23): {
                type = EnemyType.SMART_MAN;
                break;
            }
        }

        if (type == null) {
            return null;
        } else {
            return create(type, colum * ItemMapModel.SIZE_TILED - (EnemyModel.WIDTH - ItemMapModel.SIZE_TILED), row * ItemMapModel.SIZE_TILED - (EnemyModel.HEIGHT - ItemMapModel.SIZE_TILED), playerModel);
        }
    }
    //================

    public void setEnemyMoveBehavior(EnemyMoveBehavior enemyMoveBehavior) {
        this.enemyMoveBehavior = enemyMoveBehavior;
    }

    public void setEnemyBeingDestroyBehavior(EnemyBeingDestroyBehavior enemyBeingDestroyBehavior) {
        this.enemyBeingDestroyBehavior = enemyBeingDestroyBehavior;
    }

    public void setEnemyAttackBehavior(EnemyAttackBehavior enemyAttackBehavior) {
        this.enemyAttackBehavior = enemyAttackBehavior;
    }

    public EnemyMoveBehavior getEnemyMoveBehavior() {
        return enemyMoveBehavior;
    }

    public EnemyBeingDestroyBehavior getEnemyBeingDestroyBehavior() {
        return enemyBeingDestroyBehavior;
    }

    public EnemyAttackBehavior getEnemyAttackBehavior() {
        return enemyAttackBehavior;
    }

    @Override
    public GameModel getModel() {
        return (EnemyModel) model;
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ExplosionController) {
            Rectangle rectangle = this.model.getIntersectionRect(other.getModel());
            if (((EnemyModel) model).getHp() != 0
                    && rectangle.getWidth() > 10 && rectangle.getHeight() > 10) {
                System.out.println("enemyCount--" + EnemyModel.enemyCount);
                ((EnemyModel) model).setHp(0);
                Utils.playSound("enemy-out.wav", false);
            }
        }
    }

}
