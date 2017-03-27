package controllers.enemy_behavior.move;

import controllers.EnemyController;
import models.EnemyModel;
import models.PlayerModel;
import views.EnemyView;

/**
 * Created by l on 3/11/2017.
 */
public class Stop extends EnemyMoveBehavior{

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel,type, enemyController);
        //không di chuyển gì cả
       switch (type){
           case SMART_MAN:{
               moveDirection = "";
               delayToChangeMove = 300;
               if(System.currentTimeMillis() - timeStartThisMove >= delayToChangeMove){
                   enemyController.setEnemyMoveBehavior(new MoveFindPlayer());
               }
               break;
           }
       }

       setImage();
    }
}
