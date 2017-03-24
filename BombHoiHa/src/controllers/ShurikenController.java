package controllers;

import controllers.enemy_weapon.ShotDirection;
import manager.GameManager;
import models.*;
import views.AutoLoadPic;
import views.ShurikenView;

/**
 * Created by QuanT on 3/22/2017.
 */
public class ShurikenController extends GameController implements Collision {
    public static final int SPEED = 8;
    public ShotDirection type;

    public ShurikenController(ShurikenModel model, ShurikenView view) {
        super(model, view);
    }

    public ShurikenController(ShurikenModel model, ShurikenView view, GameVector gameVector, ShotDirection type) {
        super(model, view);
        this.vector = gameVector;
        this.type = type;
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
    }

    public static ShurikenController create(int x, int y, ShotDirection shotDirection){
        ShurikenController bulletController = null;
        switch (shotDirection){
            case UP:
                bulletController = new ShurikenController(
                        new ShurikenModel(x,y),
                        new ShurikenView("Bomberman/Shuriken"),
                        new GameVector(0,-SPEED),
                        ShotDirection.UP
                );
                break;
            case DOWN:
                bulletController = new ShurikenController(
                        new ShurikenModel(x,y),
                        new ShurikenView("Bomberman/Shuriken"),
                        new GameVector(0,SPEED),
                        ShotDirection.DOWN
                );
                break;
            case LEFT:
                bulletController = new ShurikenController(
                        new ShurikenModel(x,y),
                        new ShurikenView("Bomberman/Shuriken"),
                        new GameVector(-SPEED,0),
                        ShotDirection.LEFT
                );
                break;
            case RIGHT:
                bulletController = new ShurikenController(
                        new ShurikenModel(x,y),
                        new ShurikenView("Bomberman/Shuriken"),
                        new GameVector(+SPEED,0),
                        ShotDirection.RIGHT
                );
                break;
        }
        return bulletController;
    }

    @Override
    public void onContact(Collision other) {

        if(other instanceof BombController){
            ((BombController)other).explode();
            this.model.setAlive(false);
        }
    }

    @Override
    public GameModel getModel() {
        return super.getModel();
    }

    @Override
    public void run() {
        if(model instanceof ShurikenModel){
            model.moveJustUseVector(vector);
            for(int i = 0; i < GameManager.arrBlocks.size(); i++){
                if(model.getRect().intersects(GameManager.arrBlocks.get(i).getModel().getRect())&&!(GameManager.arrBlocks.get(i) instanceof BombController)){
                    model.setAlive(false);
                }
            }
        }

    }
}
