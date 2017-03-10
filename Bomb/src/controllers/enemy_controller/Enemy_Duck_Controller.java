package controllers.enemy_controller;

import controllers.GameController;
import controllers.enemy_behavior.EnemyMoveBehavior;
import controllers.enemy_behavior.RandomStupidMoveBehavior;
import models.EnemyModel;
import models.GameModel;
import views.AutoLoadPic;
import views.EnemyView;
import views.GameView;

import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public class Enemy_Duck_Controller extends GameController{
    EnemyMoveBehavior enemyMoveBehavior;
    Vector<GameModel> gameModels;

    public Enemy_Duck_Controller(GameModel model, GameView view) {
        super(model, view);
    }

    public Enemy_Duck_Controller(int x, int y, int speed, Vector<GameModel>gameModels){
        this(new EnemyModel(x,y,speed),new GameView(AutoLoadPic.enemyDuckImages.get("xuong1")));
        enemyMoveBehavior = new RandomStupidMoveBehavior();
    }

    @Override
    public void run() {
        enemyMoveBehavior.move((EnemyModel) model,(EnemyView) view,null,gameModels);
    }
}
