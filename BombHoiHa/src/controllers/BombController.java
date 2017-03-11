package controllers;

import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import manager.GameManager;
import views.GameView;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class BombController extends GameController {

    private PlayerModel playerModel;
    private int exist;

    public BombController(GameModel model, GameView view, PlayerModel playerModel) {
        super(model, view);
        GameManager.controllerManager.add(this);
        exist = 200;
        this.playerModel = playerModel;
        playerModel.increaseCountBomb();
    }

    @Override
    public void run() {
        checkPlayer();
        countDown();
    }

    private void checkPlayer() {
        if (!model.getRect().intersects(playerModel.getBottomRect(playerModel.getX(), playerModel.getY()))
                && !GameManager.arrBlocks.contains(this)) {
            GameManager.arrBlocks.add(this);
        }
    }

    private void countDown() {
        exist--;
        if (exist == 0) {

            int explosionSize = playerModel.getExplosionSize();
            new ExplosionController(model.getX(), model.getY(), "Explosions/explosion0");
            for (int i = -explosionSize; i <= explosionSize; i++) {
                if (i == -explosionSize) {
                    new ExplosionController(model.getX(), model.getY() + i * ItemMapModel.SIZE_TILED, "Explosions/explosion3");
                } else if (i == explosionSize) {
                    new ExplosionController(model.getX(), model.getY() + i * ItemMapModel.SIZE_TILED, "Explosions/explosion4");
                } else if (i != 0) {
                    new ExplosionController(model.getX(), model.getY() + i * ItemMapModel.SIZE_TILED, "Explosions/explosion1");
                }
            }
            for (int i = -explosionSize; i <= explosionSize; i++) {
                if (i == -explosionSize) {
                    new ExplosionController(model.getX() + i * ItemMapModel.SIZE_TILED, model.getY(), "Explosions/explosion6");
                } else if (i == explosionSize) {
                    new ExplosionController(model.getX() + i * ItemMapModel.SIZE_TILED, model.getY(), "Explosions/explosion5");
                } else if (i != 0) {
                    new ExplosionController(model.getX() + i * ItemMapModel.SIZE_TILED, model.getY(), "Explosions/explosion2");
                }
            }

            playerModel.reduceCountBomb();
            GameManager.arrBlocks.remove(this);
            model.setAlive(false);
        }
    }
}
