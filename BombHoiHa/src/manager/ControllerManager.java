package manager;

import controllers.EnemyController;
import controllers.GameController;
import controllers.enemy_behavior.move.Stop;

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
    public void freeze(){
        for (int i = 0; i < gameControllers.size(); i++) {
            GameController gameController = gameControllers.get(i);
            if (gameController.getModel().isAlive()) {
                if(gameController instanceof EnemyController){
                    ((EnemyController) gameController).setEnemyState(EnemyController.EnemyState.FREEZE);
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
                i--;
            }
        }
    }



}