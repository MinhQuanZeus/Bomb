package controllers.enemy_behavior.move;

import controllers.EnemyController;
import controllers.Stage;
import manager.MapManager;

/**
 * Created by QuanT on 3/12/2017.
 */
public class FreezeBehavior {
    private int count;
    private int frezzePeriod;

    public FreezeBehavior(int frezzePeriod) {
        this.frezzePeriod = frezzePeriod;
        this.count = 0;
    }

    public void run(EnemyController enemyController) {
        switch (enemyController.getEnemyState()) {
            case NORMAL:
                break;
            case FREEZE:
                count++;
                if(count > frezzePeriod) {
                    count = 0;
                    enemyController.setEnemyState(Stage.NORMAL);
                    MapManager.setCountTime(true);
                }
                break;
        }
    }
}
