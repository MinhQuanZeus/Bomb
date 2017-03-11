package controllers.enemy_behavior.attack;

import controllers.ControllerManager;
import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.EnemyView;

import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class AttackNothing extends  EnemyAttackBehavior {

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type, EnemyController enemyController, ControllerManager controllerManager) {
        super.attack(model, view, playerModel, gameModels, type, enemyController,controllerManager);
        // không làm gì cả
    }
}
