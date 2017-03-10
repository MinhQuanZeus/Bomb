package program;

import controllers.ControllerManager;
import controllers.EnemyController;
import controllers.GameController;
import controllers.PlayerController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.PlayerView;

import java.awt.*;
import java.util.Vector;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameManager {
    private ControllerManager controllerManager;
    private GameController playerController;

    public GameManager() {
        AutoLoadPic.init();

        controllerManager = new ControllerManager();
        playerController = new PlayerController(new PlayerModel(50, 50), new PlayerView(PlayerView.MOVE_DOWN));
        controllerManager.add(playerController);

        Vector<GameModel> gameModels = new Vector<>();

        //check di chuyen ran dom 4 cái tường (Chỉ để nhìn) XÓA ĐC :v
        gameModels.add(new GameModel(50,0,100,1));
        gameModels.add(new GameModel(50,100,100,1));
        gameModels.add(new GameModel(50,0,1,100));
        gameModels.add(new GameModel(150,0,1,100));
        //

        PlayerModel playerModel = (PlayerModel) playerController.getModel();
        EnemyController enemyController = EnemyController.create(EnemyController.EnemyType.DUCK,50,50,2,playerModel,gameModels,controllerManager);
        controllerManager.add(enemyController);
    }

    public void run() {
        controllerManager.run();
    }

    public void draw(Graphics graphics) {
        controllerManager.draw(graphics);
    }

    public GameController getPlayerController() {
        return playerController;
    }
}