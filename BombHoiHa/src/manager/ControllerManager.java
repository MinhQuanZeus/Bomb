package manager;

import controllers.*;
import controllers.enemy_behavior.move.Stop;
import models.PlayerModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */
public class ControllerManager {

    protected List<GameController> gameControllers;

    public ControllerManager() {
        gameControllers = new ArrayList<>();
    }

    public void add(GameController gameController) {
        if(gameController instanceof BossEnemyController){
            this.gameControllers.add(gameControllers.size(), gameController);
        }else{
            this.gameControllers.add(0, gameController);
        }
    }

    public void clear() {
        for (int i = 0; i < gameControllers.size(); i++) {
            GameController gameController = gameControllers.get(i);
            if (!(gameController instanceof PlayerController)) {
                gameController.getModel().setAlive(false);
            }
        }
    }

    public void freeze() {
        for (int i = 0; i < gameControllers.size(); i++) {
            GameController gameController = gameControllers.get(i);
            if (gameController.getModel().isAlive()) {
                if (gameController instanceof EnemyController) {
                    ((EnemyController) gameController).setEnemyState(Stage.FREEZE);
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < gameControllers.size(); i++) {
            GameController gameController = gameControllers.get(i);
            if (gameController.getModel().isAlive()) {
                gameController.draw(g);
            }
        }
    }

    public void run() {
        for (int i = 0; i < gameControllers.size(); i++) {
            GameController gameController = gameControllers.get(i);
            if (gameController.getModel().isAlive()) {
                gameController.run();
            } else {
                if (gameController instanceof EnemyController) {
                    ((PlayerModel) GameManager.playerController.getModel()).increaseScore();
                }
                if (gameController instanceof BossEnemyController) {
                    ((PlayerModel) GameManager.playerController.getModel()).increaseScore(500);
                }
                gameControllers.remove(i);
                i--;
            }
        }
    }

    public int size() {
        return gameControllers.size();
    }

    public GameController get(int i) {
        return gameControllers.get(i);
    }
}
