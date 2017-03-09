package program;

import controllers.ControllerManager;
import controllers.GameController;
import controllers.PlayerController;
import models.PlayerModel;
import views.PlayerView;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameManager {
    private ControllerManager controllerManager;
    private GameController playerController;

    public GameManager() {
        controllerManager = new ControllerManager();
        playerController = new PlayerController(new PlayerModel(50, 50), new PlayerView("Bomberman/standdown"));
        controllerManager.add(playerController);
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
