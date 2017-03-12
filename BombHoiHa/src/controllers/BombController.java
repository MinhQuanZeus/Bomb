package controllers;

import manager.MapManager;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import manager.GameManager;
import views.GameView;

import java.util.Map;

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
                        if (valueMatrix == 0) {
                            System.out.println(rowBombMatrix + " " + colBombMatrix);
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
                        if (valueMatrix == 0) {
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
                        if (valueMatrix == 0) {
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
                        if (valueMatrix == 0) {
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
            model.setAlive(false);
        }
    }

}

