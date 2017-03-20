package controllers.enemy_behavior.destroy;

import controllers.EnemyController;
import manager.ControllerManager;
import models.EnemyModel;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/10/2017.
 */
public class EnemyBeingDestroyBehavior {
    protected EnemyView view;
    protected EnemyModel model;
    protected int drawCount = 0;

    protected long lastTime = System.currentTimeMillis();
    protected long delay = 800;
    protected int howManyPicOnADestroy = 3;

    protected EnemyController.EnemyType type;
    public void destroy(
            EnemyModel model,
            EnemyView view,
            EnemyController.EnemyType type) {
        this.view = view;
        this.model = model;
        this.type = type;
    }


    public void setImage(HashMap<String, Image> destroyImagesMap) {
        HashMap<String,Image> imageMap = AutoLoadPic.imageEnemyHashMapFactory(type);
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
            }
        }
    }

    public void setHowManyPicOnADestroy(int howManyPicOnADestroy) {
        this.howManyPicOnADestroy = howManyPicOnADestroy;
    }
}
