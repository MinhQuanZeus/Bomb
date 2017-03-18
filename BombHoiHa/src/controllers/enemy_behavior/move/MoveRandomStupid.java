package controllers.enemy_behavior.move;

import controllers.EnemyController;
import controllers.enemy_behavior.attack.AttackShootABullet;
import manager.GameManager;
import models.*;
import utils.Utils;
import views.EnemyView;

/**
 * Created by l on 3/10/2017.
 */
public class MoveRandomStupid extends EnemyMoveBehavior {
    // hướng đi trước
    private String lastMove = "";


    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, type, enemyController);
        int x1 = model.getX();
        int y1 = model.getY();

        if (lastMove.equals("")) {
            switch (Utils.getRandom(4)) {
                case (0): {
                    lastMove = "trai";
                    model.move(new GameVector(-model.getSpeed(), 0), GameManager.arrBlocks);
                    break;
                }
                case (1): {
                    lastMove = "phai";
                    model.move(new GameVector(model.getSpeed(), 0), GameManager.arrBlocks);
                    break;
                }
                case (2): {
                    lastMove = "len";
                    model.move(new GameVector(0, -model.getSpeed()), GameManager.arrBlocks);
                    break;
                }
                case (3): {
                    lastMove = "xuong";
                    model.move(new GameVector(0, model.getSpeed()), GameManager.arrBlocks);
                    break;
                }
            }
        } else {
            switch (lastMove) {
                case ("len"): {
                    model.move(new GameVector(0, -model.getSpeed()), GameManager.arrBlocks);
                    break;
                }
                case ("xuong"): {
                    model.move(new GameVector(0, model.getSpeed()), GameManager.arrBlocks);
                    break;
                }
                case ("trai"): {
                    model.move(new GameVector(-model.getSpeed(), 0), GameManager.arrBlocks);
                    break;
                }
                case ("phai"): {
                    model.move(new GameVector(model.getSpeed(), 0), GameManager.arrBlocks);
                    break;
                }
            }
        }

        if (x1 == model.getX() && y1 == model.getY()) {
            lastMove = "";
        }else{
            moveDirection = lastMove;
        }


        setImage();

        switch (type) {
            case FIRE_HEAD: {
                delayToChangeMove = 2000;
                if (System.currentTimeMillis() - timeStartThisMove >= delayToChangeMove) {
                    enemyController.setEnemyAttackBehavior(new AttackShootABullet());
                    enemyController.setEnemyMoveBehavior(new Stop());
                }
            }
        }
    }
}
