package controllers;

import manager.MapManager;
import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import manager.GameManager;
import views.GameView;

import java.util.Map;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class BombController extends GameController implements Collision {

    private PlayerModel playerModel;
    private int exist;

    public BombController(GameModel model, GameView view, PlayerModel playerModel) {
        super(model, view);
        exist = 200;
        this.playerModel = playerModel;
        playerModel.increaseCountBomb();
        GameManager.collisionManager.add(this);
        GameManager.controllerManager.add(this);
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
            explode();
        }
    }

    private void explode() {
        int explosionSize = playerModel.getExplosionSize();
        new ExplosionController(model.getX(), model.getY(), "Explosions/explosion0");

        int rowBombMatrix = model.getY() / ItemMapModel.SIZE_TILED;
        int colBombMatrix = model.getX() / ItemMapModel.SIZE_TILED;

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= explosionSize; j++) {
                int indexSize = j * ItemMapModel.SIZE_TILED;
                if (i == 0) { // tren
                    if (rowBombMatrix - j < 0) {
                        break;
                    }
                    int valueMatrix = MapManager.map[rowBombMatrix - j][colBombMatrix];
                    if (valueMatrix == 0 || valueMatrix == 9) {
                        if (j != explosionSize) {
                            new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion1");
                        } else {
                            new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion3");
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion1");
                        MapManager.map[rowBombMatrix - j][colBombMatrix] = 0;
                        break;
                    }
                } else if (i == 1) { // xuong
                    if (rowBombMatrix + j > 13) {
                        break;
                    }
                    int valueMatrix = MapManager.map[rowBombMatrix + j][colBombMatrix];
                    if (valueMatrix == 0 || valueMatrix == 9) {
                        if (j != explosionSize) {
                            new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion1");
                        } else {
                            new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion4");
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion1");
                        MapManager.map[rowBombMatrix + j][colBombMatrix] = 0;
                        break;
                    }
                } else if (i == 2) { //trai
                    if (colBombMatrix - j < 0) {
                        break;
                    }
                    int valueMatrix = MapManager.map[rowBombMatrix][colBombMatrix - j];
                    if (valueMatrix == 0 || valueMatrix == 9) {
                        if (j != explosionSize) {
                            new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion2");
                        } else {
                            new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion6");
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion2");
                        MapManager.map[rowBombMatrix][colBombMatrix - j] = 0;
                        break;
                    }
                } else if (i == 3) { // phai
                    if (colBombMatrix + j > 13) {
                        break;
                    }
                    int valueMatrix = MapManager.map[rowBombMatrix][colBombMatrix + j];
                    if (valueMatrix == 0 || valueMatrix == 9) {
                        if (j != explosionSize) {
                            new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion2");
                        } else {
                            new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion5");
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion2");
                        MapManager.map[rowBombMatrix][colBombMatrix  + j] = 0;
                        break;
                    }
                }
            }
        }

        playerModel.reduceCountBomb();
        GameManager.arrBlocks.remove(this);
        MapManager.map[rowBombMatrix][colBombMatrix] = 0;
        model.setAlive(false);
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ExplosionController) {
            //explode();
            exist = 1;
        }
    }
}

