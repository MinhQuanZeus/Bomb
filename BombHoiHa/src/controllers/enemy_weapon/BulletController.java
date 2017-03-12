package controllers.enemy_weapon;

import controllers.GameController;
import controllers.PlayerController;
import manager.GameManager;
import models.*;
import views.AutoLoadPic;
import views.BulletView;
import views.GameView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by QuanT on 3/12/2017.
 */
public class BulletController extends GameController implements Collision{
    public static final int SPEED = 4;
    public ShotDirection type;


//    public long lastTimeDraw = System.currentTimeMillis();
//    public long delay = 200;
//    public int drawCount = 0;
//    public int howManyPicOnAWeapon = 3;

    public BulletController(BulletModel model, BulletView view) {
        super(model, view);
    }

    public BulletController(BulletModel model, BulletView view, GameVector gameVector,ShotDirection type) {
        super(model, view);
        this.vector = gameVector;
        this.type = type;
    }
    public static BulletController create(int x, int y, ShotDirection shotDirection){
        BulletController bulletController = null;
        switch (shotDirection){
            case UP:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new BulletView(AutoLoadPic.enemy_weapons_ImageMap.get("roundBullet")),
                        new GameVector(0,-SPEED),
                        ShotDirection.UP
                );
                break;
            case DOWN:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new BulletView(AutoLoadPic.enemy_weapons_ImageMap.get("roundBullet")),
                        new GameVector(0,SPEED),
                        ShotDirection.DOWN
                );
                break;
            case LEFT:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new BulletView(AutoLoadPic.enemy_weapons_ImageMap.get("roundBullet")),
                        new GameVector(-SPEED,0),
                        ShotDirection.LEFT
                );
                break;
            case RIGHT:
                bulletController = new BulletController(
                        new BulletModel(x,y),
                        new BulletView(AutoLoadPic.enemy_weapons_ImageMap.get("roundBullet")),
                        new GameVector(+SPEED,0),
                        ShotDirection.RIGHT
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

    @Override
    public void run() {
        if(model instanceof BulletModel){
            model.moveJustUseVector(vector);
            for(int i = 0;i < GameManager.arrBlocks.size();i++){
                if(model.getRect().intersects(GameManager.arrBlocks.get(i).getModel().getRect())){
                    model.setAlive(false);
                }
            }

        }
    }
//
//    public void setImage(){
//        if(type == ShotDirection.DOWN || type == ShotDirection.LEFT ||type == ShotDirection.UP ||type == ShotDirection.RIGHT){
//            howManyPicOnAWeapon = 6;
//            ((BulletView)view).setImage(AutoLoadPic.enemy_weapons_ImageMap.get("caulua"+drawCount));
//            long currentTime = System.currentTimeMillis();
//            if(currentTime - lastTimeDraw > delay){
//                lastTimeDraw = currentTime;
//                drawCount++;
//                if(drawCount >= howManyPicOnAWeapon){
//                    drawCount = 0;
//                }
//            }
//        }
//    }
}
