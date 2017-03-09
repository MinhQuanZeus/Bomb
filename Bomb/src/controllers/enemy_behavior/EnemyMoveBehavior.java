package controllers.enemy_behavior;

import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.EnemyView;

import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public abstract class EnemyMoveBehavior {
    EnemyModel model;
    EnemyView view;
    PlayerModel playerModel;
    Vector<GameModel> gameModels;
    boolean done;

    public boolean isDone() {
        return done;
    }

    public abstract void move(EnemyModel model,EnemyView view,PlayerModel playerModel,Vector<GameModel> gameModels);
}
