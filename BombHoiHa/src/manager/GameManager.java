package manager;

import controllers.*;
import models.PlayerModel;
import views.AutoLoadPic;
import views.PlayerView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameManager {

    public static ControllerManager controllerManager;
    public static CollisionManager collisionManager;
    public static List<GameController> arrBlocks;
    public static GameController playerController;

    private ControllerManager mapManager;

    public GameManager() {
        AutoLoadPic.init();
        controllerManager = new ControllerManager();
        arrBlocks = new ArrayList<>();
        collisionManager = new CollisionManager();
        playerController = new PlayerController(
                new PlayerModel(0, 50),
                new PlayerView(),
                arrBlocks
        );
        mapManager = new MapManager();
    }

    public void run() {
        mapManager.run();
        controllerManager.run();
        collisionManager.run();
        changeMap();
    }

    public void draw(Graphics graphics) {
        mapManager.draw(graphics);
        controllerManager.draw(graphics);
    }

    private void changeMap() {
        if (controllerManager.gameControllers.size() == 1 && playerController.getModel().isAlive()) {
            ((MapManager) mapManager).changeMap(MapManager.mapLevel + 1);
            playerController.getModel().setX(0);
            playerController.getModel().setY(50);
        }
    }
}