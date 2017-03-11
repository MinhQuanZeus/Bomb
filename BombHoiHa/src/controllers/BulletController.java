package controllers;

import models.BulletModel;
import models.Collision;
import models.GameModel;
import models.GameVector;
import views.GameView;

/**
 * Created by QuanT on 3/12/2017.
 */
public class BulletController extends GameController implements Collision{
    public static final int SPEED = 10;
    public BulletController(BulletModel model, GameView view) {
        super(model, view);
    }

    public BulletController(BulletModel model, GameView view, GameVector gameVector) {
        super(model, view);
        this.vector = gameVector;
    }
    public static BulletController create(int x, int y, ShotDerection shotDerection){
        BulletController bulletController = null;
        switch (shotDerection){
            case UP:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new GameView("bullet.png"),
                        new GameVector(0,-SPEED)
                );
                break;
            case DOWN:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new GameView("bullet.png"),
                        new GameVector(0,SPEED)
                );
                break;
            case LEFT:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new GameView("bullet.png"),
                        new GameVector(-SPEED,0)
                );
                break;
            case RIGHT:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new GameView("bullet.png"),
                        new GameVector(-SPEED,0)
                );
                break;
        }
        return bulletController;
    }

    @Override
    public GameModel getModel() {
        return super.getModel();
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof PlayerController) {
        }
    }
}
