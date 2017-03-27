package controllers.boss_behavior.move;


import controllers.BossEnemyController;
import models.BossEnemyModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.BossEnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/25/2017.
 */
public abstract class BossMoveBehavior {

    protected BossEnemyController bossEnemyController;

    protected BossEnemyModel model;
    protected BossEnemyController controller;
    protected BossEnemyView view;
    protected BossEnemyController.BossType type;

    protected int drawCount = 0;
    protected int howManyPicOnAMove = 2;

    protected long lastTime = System.currentTimeMillis();
    protected long delay = 200;


    protected long delayToChangeMove = 0;
    protected long timeStartThisMove = System.currentTimeMillis();



    public void move(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel, BossEnemyController.BossType type, BossEnemyController bossEnemyController) {
        this.model = bossEnemyModel;
        this.view = bossEnemyView;
        this.controller = bossEnemyController;
        this.type = type;
        this.bossEnemyController = bossEnemyController;
    }

    public void setImage() {
        HashMap<String, Image> map = new HashMap<>();

        map = AutoLoadPic.imageBossHashMapFactory(type);

        view.setImageShadow(map.get("bong"));
        view.setImage(map.get("dichuyen" + drawCount));
        long currentTime = System.currentTimeMillis();
        if(model.isImmunity()){
            if (currentTime - lastTime > delay) {
                lastTime = currentTime;
                drawCount++;
                if (drawCount >= howManyPicOnAMove) {
                    drawCount = 0;
                }
            }
        }else{
            drawCount = 0;
        }
    }

    public abstract void setTimeDelay();
}
