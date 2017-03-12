package controllers.enemy_behavior.attack;

import controllers.ControllerManager;
import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class EnemyAttackBehavior {
    protected EnemyView view;
    protected EnemyController.EnemyType type;
    //hướng di chuyển của enemy

    protected long lastTimeDraw = System.currentTimeMillis();
    protected long delayDraw = 170;
    protected int drawCount = 0;
    protected int howManyPicOnAttack = 3;

    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type, EnemyController enemyController, ControllerManager controllerManager){
        this.view = view;
        this.type = type;

        switch (type){
            // Nếu có enemy có số lượng ảnh attack thay đổi set howManyPicOnAttack
        }
    }

    public void setImage(){
        HashMap<String,Image> map = AutoLoadPic.imageHashMapFactory(type);
        view.setImage(map.get("tancong" + drawCount));

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTimeDraw>= delayDraw){
            lastTimeDraw = currentTime;
            drawCount++;
            if(drawCount >= howManyPicOnAttack){
                drawCount = 0;
            }
        }
    }
}