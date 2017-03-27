package controllers.boss_behavior.move;

import controllers.BossEnemyController;
import gui.GameFrame;
import models.BossEnemyModel;
import models.ItemMapModel;
import models.PlayerModel;
import utils.Utils;
import views.BossEnemyView;

/**
 * Created by l on 3/25/2017.
 */
public class Move4Corners extends BossMoveBehavior {

    private int cornersCount = 0;
    private boolean beginState = true;

    private int[] cornerX = new int[]{GameFrame.WIDTH/2/2 - BossEnemyModel.BOSS_WIDTH/2,GameFrame.WIDTH/2/2 - BossEnemyModel.BOSS_WIDTH/2,GameFrame.WIDTH/2 + GameFrame.WIDTH/2/2 - BossEnemyModel.BOSS_WIDTH/2 ,GameFrame.WIDTH/2 + GameFrame.WIDTH/2/2 - BossEnemyModel.BOSS_WIDTH/2 };
    private int[] cornerY = new int[]{GameFrame.HEIGHT/2/2 - BossEnemyModel.BOSS_HEIGHT - BossEnemyModel.BOSS_SHADOW_DISTANCE - BossEnemyModel.BOSS_HEIGHT/5 ,GameFrame.HEIGHT/2 + GameFrame.HEIGHT/2/2 - BossEnemyModel.BOSS_HEIGHT/2 - 2*ItemMapModel.SIZE_TILED ,GameFrame.HEIGHT/2 + GameFrame.HEIGHT/2/2 - BossEnemyModel.BOSS_HEIGHT/2 - 2*ItemMapModel.SIZE_TILED,GameFrame.HEIGHT/2/2 - BossEnemyModel.BOSS_HEIGHT - BossEnemyModel.BOSS_SHADOW_DISTANCE - BossEnemyModel.BOSS_HEIGHT/5};

    @Override
    public void move(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel, BossEnemyController.BossType type, BossEnemyController bossEnemyController) {
        super.move(bossEnemyModel, bossEnemyView, playerModel, type, bossEnemyController);

        if(type == BossEnemyController.BossType.BIG_HEAD){
            if(beginState){
                do{
                    cornersCount = Utils.getRandom(4);
                }while(cornerX[cornersCount] == model.getX() && cornerY[cornersCount] == model.getY());
                beginState = false;
            }
            if(((BossEnemyModel)model).getMoveLike() == 1){
                model.moveCorrectly(cornerX[cornersCount],cornerY[cornersCount]);

                if(model.getX() == cornerX[cornersCount] && cornerY[cornersCount] == model.getY()){
                    bossEnemyController.setBossMoveBehavior(new Stop());
                }
            }
        }

        setImage();
    }

    @Override
    public void setTimeDelay() {
    }
}
