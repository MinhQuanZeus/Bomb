package controllers;

import controllers.boss_behavior.attack.AttackThrowBomd;
import controllers.boss_behavior.attack.BossAttackBehavior;
import controllers.boss_behavior.destroy.BossBeingDestroyBehavior;
import controllers.boss_behavior.destroy.DestroyNormal;
import controllers.boss_behavior.move.BossMoveBehavior;
import controllers.boss_behavior.move.Move4Corners;
import controllers.boss_behavior.move.Stop;
import controllers.enemy_behavior.attack.EnemyAttackBehavior;
import controllers.enemy_behavior.destroy.EnemyBeingDestroyBehavior;
import controllers.enemy_behavior.move.EnemyMoveBehavior;
import controllers.enemy_behavior.move.FreezeBehavior;
import gui.GameFrame;
import manager.GameManager;
import models.*;
import views.AutoLoadPic;
import views.BossEnemyView;
import views.EnemyView;
import views.GameView;

/**
 * Created by l on 3/24/2017.
 */
public class BossEnemyController extends GameController implements Collision{
    protected PlayerModel playerModel;
    private BossMoveBehavior bossMoveBehavior;
    private BossBeingDestroyBehavior bossBeingDestroyBehavior;
    private BossAttackBehavior bossAttackBehavior;

    private BossType type;

    private long timeImmunity = 1000;
    private long timeStartImmunity;


    public BossEnemyController(int x,int y, int speed,BossEnemyView view, PlayerModel playerModel,BossType type) {
        super(new BossEnemyModel(x,y,speed,type), view);

        this.playerModel = playerModel;

        this.type = type;

        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
        EnemyModel.enemyCount++;
        BomdEnemyController.BOMD_COUNT = 0;
    }

    @Override
    public void run() {

                if (model instanceof BossEnemyModel) {
                    if(((BossEnemyModel) model).isImmunity()){
                        long currentTime = System.currentTimeMillis();
                        if(currentTime - timeStartImmunity >= timeImmunity){
                            ((BossEnemyModel) model).setImmunity(false);
                        }
                    }

                    if (((BossEnemyModel) model).getHp() == 0) {
                        ((BossEnemyModel) model).setDestroy(true);
                    }
                    if (((BossEnemyModel) model).isDestroy()) {
                        if (model.isAlive()) {
                            bossBeingDestroyBehavior.destroy((BossEnemyModel)model,(BossEnemyView)view,type);
                        } else {
                        }
                    } else {
                        bossMoveBehavior.move((BossEnemyModel)model,(BossEnemyView)view,playerModel,type,this);
                        bossAttackBehavior.attack((BossEnemyModel)model,(BossEnemyView)view,playerModel);
                    }
                }
    }

    public static enum BossType{
        BIG_HEAD
    }

    public static BossEnemyController create(PlayerModel playerModel,BossType type){
        BossEnemyController bossEnemyController = null;

        switch (type){
            case BIG_HEAD:{
                bossEnemyController = new BossEnemyController(GameFrame.WIDTH/2 - BossEnemyModel.BOSS_WIDTH/2,GameFrame.HEIGHT/3 -BossEnemyModel.BOSS_HEIGHT/2,8,new BossEnemyView(AutoLoadPic.imageBossHashMapFactory(type).get("dichuyen0")),playerModel,type);
                bossEnemyController.setBossMoveBehavior(new Stop());
                bossEnemyController.setBossBeingDestroyBehavior(new DestroyNormal());
                bossEnemyController.setBossAttackBehavior(new AttackThrowBomd());
                break;
            }
        }

        return bossEnemyController;
    }

    public void setBossMoveBehavior(BossMoveBehavior bossMoveBehavior) {
        this.bossMoveBehavior = bossMoveBehavior;
    }

    public void setBossAttackBehavior(BossAttackBehavior bossAttackBehavior) {
        this.bossAttackBehavior = bossAttackBehavior;
    }

    public void setBossBeingDestroyBehavior(BossBeingDestroyBehavior bossBeingDestroyBehavior) {
        this.bossBeingDestroyBehavior = bossBeingDestroyBehavior;
    }

    public BossMoveBehavior getBossMoveBehavior() {
        return bossMoveBehavior;
    }

    @Override
    public void onContact(Collision other) {
        if(other instanceof ExplosionController){
            if(!((BossEnemyModel)model).isImmunity()){
                if(((ExplosionController) other).getFrom() == 2){
                    ((BossEnemyModel)model).setImmunity(true);
                    timeStartImmunity = System.currentTimeMillis();
                    ((BossEnemyModel)model).setHp(((BossEnemyModel)model).getHp() - 1);
                }
            }
        }
    }
}
