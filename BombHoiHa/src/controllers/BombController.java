package controllers;

import controllers.enemy_weapon.ShotDirection;
import manager.GameManager;
import manager.MapManager;
import models.*;
import utils.Utils;
import views.GameView;

import java.util.List;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class BombController extends GameController implements Collision {

    private PlayerModel playerModel;
    private int exist;
    private List<GameController> arrBlocks;
    private ShotDirection slideDirection;

    public BombController(GameModel model, GameView view, List<GameController> arrBlocks, GameModel playerModel) {
        super(model, view);
        exist = 150;
        this.playerModel = (PlayerModel) playerModel;
        this.playerModel.increaseCountBomb();
        GameManager.collisionManager.add(this);
        GameManager.controllerManager.add(this);
        GameManager.arrBlocks.add(this);
        this.arrBlocks = arrBlocks;
        model.setSpeed(6);
    }


    @Override
    public void run() {
        model.move(vector, arrBlocks);
        this.vector.dx = 0;
        this.vector.dy = 0;
        if (((BombModel) model).isSlide()) {
            move(slideDirection);
        }
        countDown();
    }

    public void slide(ShotDirection slideDirection) {
        ((BombModel) model).setSlide(true);
        this.slideDirection = slideDirection;
    }

    public void move(ShotDirection shotDirection) {
        if (shotDirection == ShotDirection.DOWN) {
            this.vector.dy = model.getSpeed();
        }
        if (shotDirection == ShotDirection.UP) {
            this.vector.dy = -model.getSpeed();
        }
        if (shotDirection == ShotDirection.LEFT) {
            this.vector.dx = -model.getSpeed();
        }
        if (shotDirection == ShotDirection.RIGHT) {
            this.vector.dx = model.getSpeed();
        }
    }

    private void countDown() {
        exist--;
        if (exist == 0) {
            explode();
        }
    }

    public void explode() {
        int explosionSize = playerModel.getExplosionSize();

        if (explosionSize < 2) {
            Utils.playSound("explosion-small.wav", false);
        } else if (explosionSize < 4) {
            Utils.playSound("explosion-medium.wav", false);
        } else Utils.playSound("explosion-large.wav", false);


        int rowBombMatrix = Utils.getRowMatrix(model.getY());
        int colBombMatrix = Utils.getColMatrix(model.getX());
        int dx = colBombMatrix * ItemMapModel.SIZE_TILED;
        int dy = rowBombMatrix * ItemMapModel.SIZE_TILED;

        new ExplosionController(dx, dy, "Explosions/explosion0",2);

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
                            new ExplosionController(dx, dy - indexSize, "Explosions/explosion1",2);
                        } else {
                            new ExplosionController(dx, dy - indexSize, "Explosions/explosion3",2);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(dx, dy - indexSize, "Explosions/explosion1",2);
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
                            new ExplosionController(dx, dy + indexSize, "Explosions/explosion1",2);
                        } else {
                            new ExplosionController(dx, dy + indexSize, "Explosions/explosion4",2);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(dx, dy + indexSize, "Explosions/explosion1",2);
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
                            new ExplosionController(dx - indexSize, dy, "Explosions/explosion2",2);
                        } else {
                            new ExplosionController(dx - indexSize, dy, "Explosions/explosion6",2);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(dx - indexSize, dy, "Explosions/explosion2",2);
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
                            new ExplosionController(dx + indexSize, dy, "Explosions/explosion2",2);
                        } else {
                            new ExplosionController(dx + indexSize, dy, "Explosions/explosion5",2);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(dx + indexSize, dy, "Explosions/explosion2",2);
                        MapManager.map[rowBombMatrix][colBombMatrix + j] = 0;
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
            exist = 1;
        }

        if (other instanceof PlayerController || other instanceof EnemyController) {
            if (((BombModel) model).isSlide()) {
                ((BombModel) model).setSlide(false);
                int x;
                int y = model.getY() / ItemMapModel.SIZE_TILED * ItemMapModel.SIZE_TILED;
                if (other.getModel().getX() > this.model.getX()) {
                    x = model.getX() / ItemMapModel.SIZE_TILED * ItemMapModel.SIZE_TILED;
                } else {
                    x = (model.getX() / ItemMapModel.SIZE_TILED + 1) * ItemMapModel.SIZE_TILED;
                }
                model.setX(x);
                model.setY(y);
            }
        }
    }
}

