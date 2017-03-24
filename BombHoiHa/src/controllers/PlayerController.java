package controllers;

import controllers.enemy_weapon.BulletController;
import gui.GamePanel;
import controllers.enemy_weapon.ShotDirection;
import gui.MainPanel;
import manager.GameManager;
import manager.MapManager;
import models.*;
import utils.Utils;
import views.AnimationView;
import views.GameView;
import views.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerController extends GameController implements KeyListener, Collision {

    private BitSet bitSet;
    private List<GameController> arrBlocks;
    public static int numberShuriken = 0;
    public static final int RELOAL_SHURIKEN_SPEED = 50;
    private final int SLIDE_SPEED = 8;

    private int reloadShuriken = 0;
    private static boolean isReverse = false;
    private int reverseCount = 0;
    private static boolean isSlide = false;
    private static PlayerModel myModel;


    public PlayerController(PlayerModel model, GameView view, List<GameController> arrBlocks) {
        super(model, view);
        myModel = model;
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
        bitSet = new BitSet(256);
        this.arrBlocks = arrBlocks;
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            bitSet.clear();
        if (keyCode == KeyEvent.VK_P && GamePanel.paused) {
            return;
        } else {
            bitSet.set(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    @Override
    public void run() {
        PlayerView view = (PlayerView) this.view;

        reloadShuriken++;
        if(isReverse){
            reverseCount++;
            if(reverseCount==170){
                isReverse = false;
                reverseCount = 0;
            }
        }
        if (!((PlayerModel) model).isExplode()) {
            ((PlayerModel) model).checkImmunity();
            boolean check = model.move(vector, arrBlocks);
            if(!check){
                isSlide = false;
            }
            if(!isSlide) {
            this.vector.dx = 0;
            this.vector.dy = 0;
                if (bitSet.get(KeyEvent.VK_DOWN)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_UP)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_LEFT)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = ((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = ((PlayerModel) model).getSpeed();
                    }
                } else {
                    view.setImageHold();
                }
            }else{
                if(((PlayerModel) model).getShotDirection() == ShotDirection.DOWN){
                    view.setImage(PlayerView.MOVE_DOWN);
                    this.vector.dy = SLIDE_SPEED;
                }
                if(((PlayerModel) model).getShotDirection() == ShotDirection.UP){
                    view.setImage(PlayerView.MOVE_UP);
                    this.vector.dy = -SLIDE_SPEED;
                }
                if(((PlayerModel) model).getShotDirection() == ShotDirection.LEFT){
                    view.setImage(PlayerView.MOVE_LEFT);
                    this.vector.dx = -SLIDE_SPEED;
                }
                if(((PlayerModel) model).getShotDirection() == ShotDirection.RIGHT){
                    view.setImage(PlayerView.MOVE_RIGHT);
                    this.vector.dx = SLIDE_SPEED;
                }

            }
            if (bitSet.get(KeyEvent.VK_SPACE)) {
                bombard();
            }
            if (bitSet.get(KeyEvent.VK_CONTROL)) {
                if (numberShuriken > 0 && reloadShuriken > RELOAL_SHURIKEN_SPEED) {
                    ShurikenController shurikenController = ShurikenController.create(model.getX() + model.getWidth() / 2 - ShurikenModel.WIDTH / 2, model.getY() + model.getHeight() / 2, ((PlayerModel) model).getShotDirection());
                    reloadShuriken = 0;
                    ((PlayerModel) model).decreaseNumberShuriken();
                }
            }
        } else {
            view.explode(model);
        }
        if (reloadShuriken == 4000) {
            reloadShuriken = 0;
        }
    }

    private void bombard() {
        PlayerModel model = (PlayerModel) this.model;
        if (model.checkMaxBomb()) {
            int bombX = ((model.getX() + model.getHeight() / 2) / ItemMapModel.SIZE_TILED) * ItemMapModel.SIZE_TILED;
            int bombY = (model.getY() / ItemMapModel.SIZE_TILED + 1) * ItemMapModel.SIZE_TILED;
            int rowBombMatrix = Utils.getRowMatrix(bombY);
            int colBombMatrix = Utils.getColMatrix(bombX);
            if (MapManager.map[rowBombMatrix][colBombMatrix] == 9)
                return;
            new BombController(
                    new GameModel(bombX, bombY, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED),
                    new AnimationView("Bombs & Explosions/normalbomb", 4),
                    arrBlocks
            );
            Utils.playSound("bomb-set.wav", false);
            MapManager.map[rowBombMatrix][colBombMatrix] = 9;
        }
    }

    @Override
    public void onContact(Collision other) {

        if (other instanceof ItemMapController) {
            if (((ItemMapModel) other.getModel()).getTerrain() == Terrain.CHANGE_MAP) {
                GameManager.collisionManager.remove(other);
                MainPanel.gamePanel.addTitle(new ImageIcon("resources/System/stage-clear.png"));
                GameManager.setTransitionStart(true);
            }
        }

        if (!((PlayerModel) model).isImmunity()) {
            if (other instanceof ExplosionController) {
                Rectangle rectangle = model.getIntersectionRect(((ExplosionController) other).model);
                if (rectangle.getWidth() > 10 && rectangle.getHeight() > 10) {
                    if (!((PlayerModel) model).isExplode()) {
                        Utils.playSound("player-out.wav", false);
                        ((PlayerModel) model).setExplode(true);
                    }
                }
            }

            if (other instanceof EnemyController) {
                EnemyModel enemyModel = (EnemyModel) other.getModel();
                if (enemyModel.getBottomRect(enemyModel.getX(), enemyModel.getY()).intersects(model.getBottomRect(model.getX(), model.getY()))) {
                    if (enemyModel.getHp() != 0) {
                        if (!((PlayerModel) model).isExplode()) {
                            Utils.playSound("player-out.wav", false);
                            ((PlayerModel) model).setExplode(true);
                        }
                    }
                }
            }

            if (other instanceof BulletController) {
                if (!((PlayerModel) model).isExplode()) {
                    Utils.playSound("player-out.wav", false);
                    ((PlayerModel) model).setExplode(true);
                }
            }
        }
    }
    public BitSet getBitSet() {
        return bitSet;
    }


    public static void setSlide() {
        isSlide = true;
    }

    public static void reverseMove(){
        isReverse = true;
    }

    public static void die(){
         myModel.setExplode(true);
    }

    public static void speedDown(){
        myModel.speedDown();
    }
}
