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
        gameModels.add(new GameModel(100,100,200,1));
        gameModels.add(new GameModel(100,200,200,1));
        gameModels.add(new GameModel(100,0,1,200));
        gameModels.add(new GameModel(200,0,1,200));
        //

        PlayerModel playerModel = (PlayerModel) playerController.getModel();
        EnemyController enemyController1 = EnemyController.create(EnemyController.EnemyType.SLIM_JELLY_HEAD,110,110,2,playerModel,gameModels,controllerManager);
        controllerManager.add(enemyController1);
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