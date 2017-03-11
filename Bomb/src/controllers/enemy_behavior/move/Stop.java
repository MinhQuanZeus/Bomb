package controllers.enemy_behavior.move;

import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.EnemyView;

import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class Stop extends EnemyMoveBehavior{

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, gameModels, type, enemyController);
        //không di chuyển gì cả
    }
}
