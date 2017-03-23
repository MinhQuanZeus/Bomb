package manager;

import controllers.*;
import gui.GameFrame;
import models.*;
import utils.Utils;
import views.AnimationView;
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
    public static ControllerManager mapManager;

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