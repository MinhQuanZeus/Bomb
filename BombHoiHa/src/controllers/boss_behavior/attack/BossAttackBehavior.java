package controllers.boss_behavior.attack;

import controllers.BomdEnemyController;
import gui.GameFrame;
import manager.GameManager;
import models.BossEnemyModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.BomdEnemyView;
import views.BossEnemyView;

/**
 * Created by l on 3/25/2017.
 */
public class BossAttackBehavior {
    protected long lastTime = System.currentTimeMillis();
    protected long timeDelay = 1000;

    protected  BossEnemyModel bossEnemyModel;

    public void attack(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel){
        this.bossEnemyModel = bossEnemyModel;
    }
}
