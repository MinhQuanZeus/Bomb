package controllers.boss_behavior.destroy;

import controllers.BossEnemyController;
import controllers.EnemyController;
import models.BossEnemyModel;
import models.EnemyModel;
import views.AutoLoadPic;
import views.BossEnemyView;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/25/2017.
 */
public class BossBeingDestroyBehavior {

    protected BossEnemyView view;
    protected BossEnemyModel model;
    protected BossEnemyController.BossType type;

    protected int drawCount = 0;

    protected long lastTime = System.currentTimeMillis();
    protected long delay = 200;
    protected int howManyPicOnADestroy = 3;

    public void destroy(
            BossEnemyModel model,
            BossEnemyView view,
            BossEnemyController.BossType type) {
        this.view = view;
        this.model = model;
        this.type = type;
        switch (type){
            case BIG_HEAD:{
                howManyPicOnADestroy = 7;
                break;
            }
        }
    }

    public void setImage() {
        HashMap<String,Image> imageMap = AutoLoadPic.imageBossHashMapFactory(type);
        view.setImage(imageMap.get("chet" + drawCount));
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastTime > delay) {
            model.setHeight(model.getHeight() - 5);
            model.setWidth(model.getWidth() - 5);
            lastTime = currentTime;
            drawCount++;
            if (drawCount >= howManyPicOnADestroy) {
                drawCount = 0;
                model.setAlive(false);
                EnemyModel.enemyCount--;
            }
        }
    }
}
