package controllers.boss_behavior.destroy;

import controllers.BossEnemyController;
import models.BossEnemyModel;
import views.BossEnemyView;

/**
 * Created by l on 3/25/2017.
 */
public class DestroyNormal extends BossBeingDestroyBehavior {

    @Override
    public void destroy(BossEnemyModel model, BossEnemyView view, BossEnemyController.BossType type) {
        super.destroy(model, view, type);

        setImage();
    }
}
