package controllers.enemy_behavior;

import controllers.ControllerManager;
import controllers.EnemyController;
import models.EnemyModel;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/10/2017.
 */
public class DestroyNormal extends EnemyBeingDestroyBehavior{

    @Override
    public void destroy(EnemyModel model, EnemyView view, ControllerManager controllerManager) {
        super.destroy(model,view,controllerManager);
        setImage(AutoLoadPic.explosionImageMap);
    }
}
