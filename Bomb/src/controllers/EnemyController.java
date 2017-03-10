package controllers;

import controllers.enemy_behavior.EnemyMoveBehavior;
import controllers.enemy_behavior.RandomStupidMoveBehavior;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.EnemyView;
import views.GameView;

import java.util.Vector;

/**
 * Created by QuanT on 3/9/2017.
 */
public class EnemyController extends GameController {
    protected EnemyMoveBehavior enemyMoveBehavior;
    protected Vector<GameModel> gameModels;
    protected PlayerModel playerModel;

    public EnemyController(int x, int y, int speed,EnemyView enemyView, PlayerModel playerModel, Vector<GameModel>gameModels){
        this(new EnemyModel(x,y,speed),enemyView,playerModel,gameModels);
    }


    public EnemyController(GameModel model, GameView view, PlayerModel playerModel,Vector<GameModel> gameModels) {
        super(model, view);
        this.playerModel = playerModel;
        this.gameModels = gameModels;
    }

    @Override
    public void run() {
       enemyMoveBehavior.move((EnemyModel) model,(EnemyView) view,playerModel,gameModels);
    }

    public void setEnemyMoveBehavior(EnemyMoveBehavior enemyMoveBehavior) {
        this.enemyMoveBehavior = enemyMoveBehavior;
    }

    public enum EnemyType{
        DUCK
    }
    public static EnemyController create(EnemyType type,int x,int y,int speed,PlayerModel playerModel,Vector<GameModel> gameModels){
        EnemyController enemyController = null;

        if(type == EnemyType.DUCK){
            enemyController = new EnemyController(x,y,speed,new EnemyView(AutoLoadPic.enemyDuckImages.get("xuong0")),playerModel,gameModels);
            enemyController.setEnemyMoveBehavior(new RandomStupidMoveBehavior());
        }

        return enemyController;
    }
}
