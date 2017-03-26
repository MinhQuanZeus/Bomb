package manager;

import controllers.EnemyController;
import controllers.GameController;
import controllers.PlayerController;
import controllers.Stage;
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
        this.gameControllers.add(0, gameController);
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
                gameControllers.remove(i);
                if (gameController instanceof EnemyController) {
                    ((PlayerModel) GameManager.playerController.getModel()).increaseScore();
                }
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
