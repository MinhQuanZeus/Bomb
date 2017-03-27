package controllers;

import manager.GameManager;
import manager.MapManager;
import models.BomdEnemyModel;
import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import utils.Utils;
import views.AutoLoadPic;
import views.BomdEnemyView;
import views.GameView;

/**
 * Created by l on 3/25/2017.
 */
public class BomdEnemyController extends GameController implements Collision{
    public static int BOMD_COUNT = 0;

    private int exist;
    private int explosionSize;

    private int drawCountBomd = 1;
    private int drawCountSmoke = 0;
    private long timeDelayBomd = 200;
    private long timeStartBomd = System.currentTimeMillis();
    private long timeDelaySmoke = 500;
    private long timeStartSmoke = System.currentTimeMillis();

    public BomdEnemyController(int x,int y,int yMoveTo,GameView view,int explosionSize){
        this(new BomdEnemyModel(x,y,yMoveTo),view);
        GameManager.collisionManager.add(this);
        GameManager.controllerManager.add(this);
        GameManager.arrBlocks.add(this);
        exist = 150;
        this.explosionSize = explosionSize;

        BOMD_COUNT += 1;

    }

    public BomdEnemyController(GameModel model, GameView view) {
        super(model, view);
    }

    @Override
    public void run() {
        if(model instanceof BomdEnemyModel){
            ((BomdEnemyModel) model).calculateShadow();
            if(((BomdEnemyModel) model).isStop()){
                ((BomdEnemyView)view).setSmokeImage(AutoLoadPic.enemy_weapons_ImageMap.get("smoke" + drawCountSmoke));
                ((BomdEnemyView)view).setShadowImage(AutoLoadPic.enemy_weapons_ImageMap.get("bong"));
                ((BomdEnemyView)view).setImage(AutoLoadPic.enemy_weapons_ImageMap.get("bbomd" + drawCountBomd));

                long currentTime = System.currentTimeMillis();


                if(currentTime - timeStartBomd > timeDelayBomd){
                    timeStartBomd = currentTime;
                    drawCountBomd++;

                    if(drawCountBomd >= 4){
                        drawCountBomd = 1;
                    }
                }

                if(currentTime - timeStartSmoke > timeDelaySmoke){
                    timeStartSmoke = currentTime;
                    drawCountSmoke++;
                }


                countDown();
            }else{
                ((BomdEnemyModel) model).fallDown();
                ((BomdEnemyView)view).setShadowImage(AutoLoadPic.enemy_weapons_ImageMap.get("bong"));
                ((BomdEnemyView)view).setImage(AutoLoadPic.enemy_weapons_ImageMap.get("bbomd0"));
            }
        }
    }

    private void countDown() {
        exist--;
        if (exist == 0) {
            explode();
        }
    }

    private void explode() {

        if (explosionSize < 2){
            Utils.playSound("explosion-small.wav",false);
        }
        else if (explosionSize < 4){
            Utils.playSound("explosion-medium.wav",false);
        }
        else Utils.playSound("explosion-large.wav",false);

        new ExplosionController(model.getX(), model.getY(), "Explosions/explosion0",1);

        int rowBombMatrix = Utils.getRowMatrix(model.getY());
        int colBombMatrix = Utils.getColMatrix(model.getX());

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
                            new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion1",1);
                        } else {
                            new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion3",1);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX(), model.getY() - indexSize, "Explosions/explosion1",1);
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
                            new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion1",1);
                        } else {
                            new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion4",1);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX(), model.getY() + indexSize, "Explosions/explosion1",1);
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
                            new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion2",1);
                        } else {
                            new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion6",1);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX() - indexSize, model.getY(), "Explosions/explosion2",1);
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
                            new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion2",1);
                        } else {
                            new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion5",1);
                        }
                    } else if (valueMatrix == 1) {
                        break;
                    } else if (valueMatrix == 2) {
                        new ExplosionController(model.getX() + indexSize, model.getY(), "Explosions/explosion2",1);
                        MapManager.map[rowBombMatrix][colBombMatrix  + j] = 0;
                        break;
                    }
                }
            }
        }

        GameManager.arrBlocks.remove(this);
        BOMD_COUNT -= 1;
        model.setAlive(false);
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ExplosionController) {
            exist = 1;
        }
    }
}
