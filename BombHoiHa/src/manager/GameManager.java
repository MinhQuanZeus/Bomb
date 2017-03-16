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
        mapManager = new MapManager();

        playerController = new PlayerController(
                new PlayerModel(0, 50),
                new PlayerView(),
                arrBlocks
        );


        EnemyController e = EnemyController.create(EnemyController.EnemyType.SLIM_JELLY_HEAD, 0, 50, 2, (PlayerModel) playerController.getModel(), arrBlocks, controllerManager);
        EnemyController e2 = EnemyController.create(EnemyController.EnemyType.DUCK, 0, 50, 2, (PlayerModel) playerController.getModel(), arrBlocks, controllerManager);
        EnemyController e1 = EnemyController.create(EnemyController.EnemyType.FIRE_HEAD, 0, 50, 3, (PlayerModel) playerController.getModel(), arrBlocks, controllerManager);
        controllerManager.add(e);
        controllerManager.add(e1);
        controllerManager.add(e2);
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