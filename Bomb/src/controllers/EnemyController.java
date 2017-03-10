package controllers;

import controllers.enemy_behavior.EnemyMoveBehavior;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.GameView;

import java.util.Vector;

/**
 * Created by QuanT on 3/9/2017.
 */
public class EnemyController extends GameController {
    protected EnemyMoveBehavior enemyMoveBehavior;
    protected Vector<GameModel> gameModels;
    protected PlayerModel playerModel;
    protected int drawCount;


    public EnemyController(GameModel model, GameView view, PlayerModel playerModel,Vector<GameModel> gameModels) {
        super(model, view);
        this.playerModel = playerModel;
        this.gameModels = gameModels;
        drawCount = 0;
    }

    @Override
    public void run() {

    }
}
