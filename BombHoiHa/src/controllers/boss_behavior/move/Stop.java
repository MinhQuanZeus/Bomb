package controllers.boss_behavior.move;

import controllers.BossEnemyController;
import models.BossEnemyModel;
import models.PlayerModel;
import views.BossEnemyView;

/**
 * Created by l on 3/25/2017.
 */
public class Stop extends BossMoveBehavior{

    private int lastHp;
    private boolean beginState = true;

    @Override
    public void move(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel, BossEnemyController.BossType type, BossEnemyController bossEnemyController) {
        super.move(bossEnemyModel, bossEnemyView, playerModel, type, bossEnemyController);

        if(beginState){
            beginState = false;
            lastHp = bossEnemyModel.getHp();
        }
        if(type == BossEnemyController.BossType.BIG_HEAD){
            setTimeDelay();
            if(model.getHp() == BossEnemyModel.BOSS_BIGHEAD_MAD_HP && ((BossEnemyModel)model).getMoveLike() == 0){
                lastTime = System.currentTimeMillis();
                ((BossEnemyModel)model).increaseMoveLike();
                bossEnemyController.setBossMoveBehavior(new Move4Corners());

            }else if(((BossEnemyModel)model).getMoveLike() == 1){
                int currentHp = bossEnemyModel.getHp();

                if(currentHp < lastHp){
                    delayToChangeMove = 1;
                }

                long currentTime = System.currentTimeMillis();
                if(currentTime - lastTime > delayToChangeMove){

                    bossEnemyController.setBossMoveBehavior(new Move4Corners());
                }
            }
        }

        setImage();
    }

    @Override
    public void setTimeDelay() {
        switch (type){
            case BIG_HEAD:{
                delayToChangeMove = 8000;
                break;
            }
        }
    }
}
