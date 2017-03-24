package controllers;

import controllers.enemy_weapon.BulletController;
import controllers.enemy_weapon.ShotDirection;
import manager.GameManager;
import manager.MapManager;
import models.*;
import utils.Utils;
import views.BombView;
import views.GameView;
import views.PlayerView;

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
    public static final int RELOAL_SHURIKEN_SPEED = 50;
    private ShotDirection shotDirection = ShotDirection.RIGHT;
    private int reloadShuriken = 0;
    private boolean isReverse = false;
    private int reverseCount = 0;
    private boolean isSlide = false;
    private final int SLIDE_SPEED = 8;

    public PlayerController(PlayerModel model, GameView view, List<GameController> arrBlocks) {
        super(model, view);
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
        bitSet.set(e.getKeyCode());
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
                        shotDirection = ShotDirection.UP;
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    } else {
                        shotDirection = ShotDirection.DOWN;
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_UP)) {
                    if (isReverse) {
                        shotDirection = ShotDirection.DOWN;
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    } else {
                        shotDirection = ShotDirection.UP;
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_LEFT)) {
                    if (isReverse) {
                        shotDirection = ShotDirection.RIGHT;
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = ((PlayerModel) model).getSpeed();
                    } else {
                        shotDirection = ShotDirection.LEFT;
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                    if (isReverse) {
                        shotDirection = ShotDirection.LEFT;
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -((PlayerModel) model).getSpeed();
                    } else {
                        shotDirection = ShotDirection.RIGHT;
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = ((PlayerModel) model).getSpeed();
                    }
                } else {
                    view.setImageHold();
                }
            }else{
                if(shotDirection == ShotDirection.DOWN){
                    view.setImage(PlayerView.MOVE_DOWN);
                    this.vector.dy = SLIDE_SPEED;
                }
                if(shotDirection == ShotDirection.UP){
                    view.setImage(PlayerView.MOVE_UP);
                    this.vector.dy = -SLIDE_SPEED;
                }
                if(shotDirection == ShotDirection.LEFT){
                    view.setImage(PlayerView.MOVE_LEFT);
                    this.vector.dx = -SLIDE_SPEED;
                }
                if(shotDirection == ShotDirection.RIGHT){
                    view.setImage(PlayerView.MOVE_RIGHT);
                    this.vector.dx = SLIDE_SPEED;
                }

            }
            if (bitSet.get(KeyEvent.VK_SPACE)) {
                bombard();
            }
            if (bitSet.get(KeyEvent.VK_CONTROL)) {
                if(((PlayerModel) model).getNumberShuriken()>0&&reloadShuriken>RELOAL_SHURIKEN_SPEED){
                    ShurikenController shurikenController = ShurikenController.create(model.getX()+model.getWidth()/2 - ShurikenModel.WIDTH/2,model.getY()+model.getHeight()/2,shotDirection);
                    reloadShuriken = 0;
                    ((PlayerModel) model).decreaseNumberShuriken();
                }
            }
        } else {
            view.explode(model);
        }
        if(reloadShuriken==4000){
            reloadShuriken=0;
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
                    new BombView("Bombs & Explosions/normalbomb")
            );
            Utils.playSound("bomb-set.wav", false);
            MapManager.map[rowBombMatrix][colBombMatrix] = 9;
        }
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ItemController) {
            if (((ItemController) other).getType() == ItemType.SPEED_UP) {
                ((PlayerModel) model).speedUp();
            }
            if (((ItemController) other).getType() == ItemType.EXPAND_EXPLOSIVE) {
                ((PlayerModel) model).expandExplosionSize();
            }
            if (((ItemController) other).getType() == ItemType.EXPAND_BOMB) {
                ((PlayerModel) model).expandMaxBomb();
            }
            if (((ItemController) other).getType() == ItemType.FREEZE) {
                GameManager.controllerManager.freeze();
                MapManager.setCountTime(false);
            }
            if (((ItemController) other).getType() == ItemType.BONUS_TIME) {
                MapManager.bounousTime();
            }
            if (((ItemController) other).getType() == ItemType.SHURIKEN) {
                ((PlayerModel) model).bonusShuriken();
            }
            if (((ItemController) other).getType() == ItemType.BONUS_LIFE) {
                ((PlayerModel) model).bonusLife();
            }
            if (((ItemController) other).getType() == ItemType.REVERSE_MOVE) {
                isReverse = true;
            }
            if (((ItemController) other).getType() == ItemType.DIE) {
                ((PlayerModel) model).setExplode(true);
            }
            if (((ItemController) other).getType() == ItemType.SPIDERWEB) {
                ((PlayerModel) model).speedDown();
            }
            if (((ItemController) other).getType() == ItemType.SLIDE) {
                isSlide = true;
            }
        }

        if (other instanceof ItemMapController) {
            if (((ItemMapModel) other.getModel()).getTerrain() == Terrain.CHANGE_MAP) {
                ((MapManager) GameManager.mapManager).changeMap(MapManager.mapLevel + 1);
                model.setX(0);
                model.setY(50);
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
    public void stopSlide(){
        for(int i = 0; i < GameManager.arrBlocks.size(); i++){
            if(model.getRect().intersects(GameManager.arrBlocks.get(i).getModel().getRect())){
                System.out.println("Stop");
                isSlide = false;
                this.vector.dx = 0;
                this.vector.dy = 0;
            }
        }
    }


}
