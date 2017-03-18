package controllers.enemy_behavior.destroy;

import controllers.EnemyController;
import manager.ControllerManager;
import models.EnemyModel;
import views.AutoLoadPic;
import views.EnemyView;

/**
 * Created by l on 3/10/2017.
 */
public class DestroyNormal extends EnemyBeingDestroyBehavior {

    @Override
    public void destroy(EnemyModel model, EnemyView view, EnemyController.EnemyType type) {
        super.destroy(model, view,type);
        setImage(AutoLoadPic.explosionImageMap);
    }
}
