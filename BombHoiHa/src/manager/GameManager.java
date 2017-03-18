package manager;

import controllers.*;
import models.PlayerModel;
import views.AutoLoadPic;
import views.PlayerView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    }

    public void draw(Graphics graphics) {
        mapManager.draw(graphics);
        controllerManager.draw(graphics);
    }
}