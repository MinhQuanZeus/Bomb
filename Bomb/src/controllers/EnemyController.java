package controllers;

import controllers.enemy_behavior.destroy.DestroyNormal;
import controllers.enemy_behavior.destroy.EnemyBeingDestroyBehavior;
import controllers.enemy_behavior.move.EnemyMoveBehavior;
import controllers.enemy_behavior.move.MoveRandomStupid;
import controllers.enemy_behavior.move.MoveRandom_And_Jump;
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

// dùng hàm create trong enemycontroller: cần vị trí x,y, tốc độ   tuy nhiên máu là mặc định
public class EnemyController extends GameController {
    protected Vector<GameModel> gameModels;
    protected PlayerModel playerModel;
    protected EnemyType type;
    protected ControllerManager controllerManager;

    protected EnemyMoveBehavior enemyMoveBehavior;
    protected EnemyBeingDestroyBehavior enemyBeingDestroyBehavior;

    // cần enum type để vẽ trong movebehavior, cần managerController để thêm việc tấn công, quái bắn lửa
    public EnemyController(int x, int y, int speed,int hp,EnemyView enemyView, PlayerModel playerModel, Vector<GameModel>gameModels,EnemyType type,ControllerManager controllerManager){
        this(new EnemyModel(x,y,speed,hp,type),enemyView,playerModel,gameModels,type,controllerManager);
    }


    public EnemyController(GameModel model, GameView view, PlayerModel playerModel,Vector<GameModel> gameModels,EnemyType type,ControllerManager controllerManager) {
        super(model, view);
        this.playerModel = playerModel;
        this.gameModels = gameModels;
        this.type = type;
        this.controllerManager = controllerManager;
    }

    @Override
    public void run() {
        if(model instanceof EnemyModel){
            if(((EnemyModel) model).getHp() == 0){
                ((EnemyModel) model).setDestroy(true);
            }
            if(((EnemyModel) model).isDestroy()){
                if(model.isAlive()){
                    enemyBeingDestroyBehavior.destroy((EnemyModel) model,(EnemyView)view,controllerManager);
                }else{
                }
            }else{
                enemyMoveBehavior.move((EnemyModel) model,(EnemyView) view,playerModel,gameModels,type,this);
            }
        }
    }

    static public enum EnemyType{
        DUCK,
        SLIM_JELLY_HEAD
    }

    //=========CREATE ENEMY
    public static EnemyController create(EnemyType type,int x,int y,int speed,PlayerModel playerModel,Vector<GameModel> gameModels,ControllerManager controllerManager){
        EnemyController enemyController = null;
        // playerModel để truy đuổi nếu cần, gamemodels để check có đi đc ko,
        // type để có thể thực hiện việc vẽ hình di chuyển setImage trong MoveBehavior
        switch (type){
            case DUCK:{
                enemyController = new EnemyController(x,y,speed,1,new EnemyView(AutoLoadPic.enemy_Duck_Image_Hashmap.get("xuong0")),playerModel,gameModels,type,controllerManager);
                enemyController.setEnemyMoveBehavior(new MoveRandomStupid());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
                break;
            }
            case SLIM_JELLY_HEAD:{
                enemyController = new EnemyController(x,y,speed,1,new EnemyView(AutoLoadPic.enemy_SlimJellyHead_ImageMap.get("xuong0")),playerModel,gameModels,type,controllerManager);
                enemyController.setEnemyMoveBehavior(new MoveRandom_And_Jump());
                enemyController.setEnemyBeingDestroyBehavior(new DestroyNormal());
            }
        }

        return enemyController;
    }

    public void setEnemyMoveBehavior(EnemyMoveBehavior enemyMoveBehavior) {
        this.enemyMoveBehavior = enemyMoveBehavior;
    }

    public void setEnemyBeingDestroyBehavior(EnemyBeingDestroyBehavior enemyBeingDestroyBehavior) {
        this.enemyBeingDestroyBehavior = enemyBeingDestroyBehavior;
    }

    @Override
    public GameModel getModel() {
        return (EnemyModel)model;
    }

    public EnemyModel getEnemyModel(){
        return (EnemyModel)model;
    }
}
