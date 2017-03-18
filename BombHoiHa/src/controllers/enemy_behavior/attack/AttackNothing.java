package controllers.enemy_behavior.attack;

import controllers.EnemyController;
import manager.ControllerManager;
import models.EnemyModel;
import models.PlayerModel;
import views.EnemyView;

/**
 * Created by l on 3/11/2017.
 */
public class AttackNothing extends EnemyAttackBehavior {

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        super.attack(model, view, playerModel, type, enemyController);
        // không làm gì cả
    }
}
