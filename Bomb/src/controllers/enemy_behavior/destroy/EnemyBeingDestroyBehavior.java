package controllers.enemy_behavior.destroy;

import controllers.ControllerManager;
import controllers.EnemyController;
import models.EnemyModel;
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
    protected long delay = 170;
    protected int howManyPicOnADestroy = 9;

    public  void destroy(
            EnemyModel model,
            EnemyView view,
            ControllerManager controllerManager){
        this.view = view;
        this.model = model;
    }


    public void setImage(HashMap<String,Image> destroyImagesMap) {
        view.setImage(destroyImagesMap.get("explosion" + drawCount));
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >  delay) {
            lastTime = currentTime;
            drawCount++;
            if(drawCount >= howManyPicOnADestroy){
                drawCount = 0;
                model.setAlive(false);
            }
        }
    }

    public void setHowManyPicOnADestroy(int howManyPicOnADestroy) {
        this.howManyPicOnADestroy = howManyPicOnADestroy;
    }
}
