package manager;

import controllers.*;
import models.PlayerModel;
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

    private ControllerManager mapManager;
    private GameController playerController;

    public GameManager() {
        controllerManager = new ControllerManager();
        arrBlocks = new ArrayList<>();
        collisionManager = new CollisionManager();
        mapManager = new MapManager();

        playerController = new PlayerController(
                new PlayerModel(0, 50),
                new PlayerView(),
                arrBlocks
        );
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

    public GameController getPlayerController() {
        return playerController;
    }
}