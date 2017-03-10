package program;

import controllers.ControllerManager;
import controllers.GameController;
import controllers.PlayerController;
import controllers.enemy_controller.Enemy_Duck_Controller;
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
        PlayerModel playerModel = (PlayerModel) playerController.getModel();
        controllerManager.add(new Enemy_Duck_Controller(50,50,2,playerModel,gameModels));
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