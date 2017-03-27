package controllers.boss_behavior.attack;

import controllers.BomdEnemyController;
import gui.GameFrame;
import manager.GameManager;
import models.BossEnemyModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.BomdEnemyView;
import views.BossEnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/25/2017.
 */
public class BossAttackBehavior {
    protected long lastTime = System.currentTimeMillis();
    protected long timeDelay = 2000;
    protected BossEnemyView bossEnemyView;
    protected int drawCount = 0;

    protected  BossEnemyModel bossEnemyModel;

    public void attack(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel){
        this.bossEnemyModel = bossEnemyModel;
        this.bossEnemyView = bossEnemyView;
    }

    public void setImage() {
        HashMap<String, Image> map = new HashMap<>();


        if(BomdEnemyController.BOMD_COUNT != 0){
            long current = System.currentTimeMillis();

            if(current - lastTime > timeDelay){
                bossEnemyView.setImageElec(AutoLoadPic.enemy_weapons_ImageMap.get("elec" + drawCount));

                drawCount++;
                if(drawCount >= 8){
                    drawCount = 0;
                }
            }
        }else{
            bossEnemyView.setImageElec(null);

        }
    }
}
