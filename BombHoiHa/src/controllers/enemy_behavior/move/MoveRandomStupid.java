package controllers.enemy_behavior.move;

import controllers.EnemyController;
import controllers.GameController;
import controllers.enemy_behavior.attack.AttackShootABullet;
import models.*;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.List;

/**
 * Created by l on 3/10/2017.
 */
public class MoveRandomStupid extends EnemyMoveBehavior {
    // hướng đi trước
    private String lastMove = "";


    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, List<GameController> gameControllers, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, gameControllers, type, enemyController);
        int x1 = model.getX();
        int y1 = model.getY();

        if (lastMove.equals("")) {
            switch (Utils.getRandom(4)) {
                case (0): {
                    lastMove = "trai";
                    moveDirection = "trai";
                    model.move(new GameVector(-model.getSpeed(), 0), gameControllers);
                    break;
                }
                case (1): {
                    lastMove = "phai";
                    moveDirection = "phai";
                    model.move(new GameVector(model.getSpeed(), 0), gameControllers);
                    break;
                }
                case (2): {
                    lastMove = "len";
                    moveDirection = "len";
                    model.move(new GameVector(0, -model.getSpeed()), gameControllers);
                    break;
                }
                case (3): {
                    lastMove = "xuong";
                    moveDirection = "xuong";
                    model.move(new GameVector(0, model.getSpeed()), gameControllers);
                    break;
                }
            }
        } else {
            switch (lastMove) {
                case ("len"): {
                    model.move(new GameVector(0, -model.getSpeed()), gameControllers);
                    break;
                }
                case ("xuong"): {
                    model.move(new GameVector(0, model.getSpeed()), gameControllers);
                    break;
                }
                case ("trai"): {
                    model.move(new GameVector(-model.getSpeed(), 0), gameControllers);
                    break;
                }
                case ("phai"): {
                    model.move(new GameVector(model.getSpeed(), 0), gameControllers);
                    break;
                }
            }
        }

        if (x1 == model.getX() && y1 == model.getY()) {
            lastMove = "";
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
