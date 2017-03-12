package controllers.enemy_behavior.attack;

import controllers.GameController;
import manager.ControllerManager;
import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.EnemyView;

import java.util.List;

/**
 * Created by l on 3/11/2017.
 */
public class AttackNothing extends EnemyAttackBehavior {

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, List<GameController> gameControllers, EnemyController.EnemyType type, EnemyController enemyController, ControllerManager controllerManager) {
        super.attack(model, view, playerModel, gameControllers, type, enemyController, controllerManager);
        // không làm gì cả
    }
}
