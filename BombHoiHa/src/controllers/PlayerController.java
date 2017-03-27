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
public class PlayerController extends GameController implements Collision, KeyListener {


    public static final int RELOAD_SHURIKEN_SPEED = 50;
    public static final int SLIDE_SPEED = 8;

    protected int reloadShuriken = 0;
    protected boolean isReverse = false;
    protected BitSet bitSet = new BitSet(256);
    protected List<GameController> arrBlocks;
    protected boolean isSlide = false;
    private int reverseCount = 0;
    private int countDownKickPlayer = ItemController.MAX_KICK_TIME;
    private Stage playerStage;
    private PlayerFreezeBehavior playerFreezeBehavior;

    public PlayerController(PlayerModel model, List<GameController> arrBlocks, String urlImage, PlayerFreezeBehavior playerFreezeBehavior) {
        super(model, new PlayerView(urlImage));
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
        this.playerFreezeBehavior = playerFreezeBehavior;
        playerStage = Stage.NORMAL;
        this.arrBlocks = arrBlocks;
    }

    @Override
    public void run() {

        if (isReverse) {
            reverseCount++;
            if (reverseCount == 170) {
                isReverse = false;
                reverseCount = 0;
            }
        } else {
            reverseCount = 0;
        }

        reloadShuriken++;
        if (reloadShuriken == 4000) {
            reloadShuriken = 0;
        }

        if (((PlayerModel) model).isKick()) {
            countDownKickPlayer--;
            if (countDownKickPlayer == 0) {
                ((PlayerModel) model).setKick(false);
                countDownKickPlayer = ItemController.MAX_KICK_TIME;
            }
        } else {
            countDownKickPlayer = ItemController.MAX_KICK_TIME;
        }

        switch (playerStage) {
            case NORMAL:
                checkBitSet();
            case FREEZE:
                break;
        }
        if (playerFreezeBehavior != null) {
            playerFreezeBehavior.run(this);
        }

    }

    public void checkBitSet() {
        PlayerView view = (PlayerView) this.view;
        if (!((PlayerModel) model).isExplode()) {
            ((PlayerModel) model).checkImmunity();
            boolean check = model.move(vector, arrBlocks);
            if (!check) {
                isSlide = false;
            }
            if (!isSlide) {
                this.vector.dx = 0;
                this.vector.dy = 0;
                if (bitSet.get(KeyEvent.VK_S)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_W)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_A)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_D)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = model.getSpeed();
                    }
                } else {
                    view.setImageHold();
                }
            } else {
                if (((PlayerModel) model).getShotDirection() == ShotDirection.DOWN) {
                    view.setImage(PlayerView.MOVE_DOWN);
                    this.vector.dy = SLIDE_SPEED;
                }
                if (((PlayerModel) model).getShotDirection() == ShotDirection.UP) {
                    view.setImage(PlayerView.MOVE_UP);
                    this.vector.dy = -SLIDE_SPEED;
                }
                if (((PlayerModel) model).getShotDirection() == ShotDirection.LEFT) {
                    view.setImage(PlayerView.MOVE_LEFT);
                    this.vector.dx = -SLIDE_SPEED;
                }
                if (((PlayerModel) model).getShotDirection() == ShotDirection.RIGHT) {
                    view.setImage(PlayerView.MOVE_RIGHT);
                    this.vector.dx = SLIDE_SPEED;
                }

            }
            if (bitSet.get(KeyEvent.VK_J)) {
                bombard();
            }
            if (bitSet.get(KeyEvent.VK_K)) {
                if (((PlayerModel) model).getNumberShuriken() > 0 && reloadShuriken > RELOAD_SHURIKEN_SPEED) {
                    ShurikenController.create(model.getX() + model.getWidth() / 2 - ShurikenModel.WIDTH / 2, model.getY() + model.getHeight() / 2, ((PlayerModel) model).getShotDirection());
                    reloadShuriken = 0;
                    ((PlayerModel) model).decreaseNumberShuriken();
                }
            }
        } else {
            view.explode(model);
        }
    }

    public void bombard() {
        PlayerModel model = (PlayerModel) this.model;
        if (model.checkMaxBomb()) {
            int rowBombMatrix = Utils.getRowMatrix(model.getYRect() + ItemMapModel.SIZE_TILED / 2);
            int colBombMatrix = Utils.getColMatrix(model.getXRect() + ItemMapModel.SIZE_TILED / 2);
            if (MapManager.map[rowBombMatrix][colBombMatrix] == 9)
                return;
            new BombController(
                    new BombModel(colBombMatrix * ItemMapModel.SIZE_TILED, rowBombMatrix * ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED),
                    new AnimationView("Bombs & Explosions/normalbomb", 4),
                    arrBlocks,
                    model
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
                MainPanel.gamePanel.addTitle(GamePanel.clearIcon);
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

            if(other instanceof BossEnemyController){
                Rectangle r = new Rectangle(other.getModel().getX() + 10,other.getModel().getY() + other.getModel().getHeight()/2,other.getModel().getWidth() - 20,BossEnemyModel.BOSS_HEIGHT/2 + BossEnemyModel.BOSS_SHADOW_DISTANCE + BossEnemyModel.BOSS_HEIGHT/5);
                if(r.intersects(new Rectangle(model.getX(),model.getY(),model.getHeight(),model.getWidth()))){
                    if (!((PlayerModel) model).isExplode()) {
                        Utils.playSound("player-out.wav", false);
                        ((PlayerModel) model).setExplode(true);
                    }
                }
            }
        }
    }

    public void setSlide() {
        isSlide = true;
    }

    public void reverseMove() {
        isReverse = true;
        reverseCount = 0;
    }

    public void resetCountDownKickPlayer() {
        countDownKickPlayer = ItemController.MAX_KICK_TIME;
    }

    public void die() {
        ((PlayerModel) model).setExplode(true);
    }

    public void speedDown() {
        ((PlayerModel) model).speedDown();
    }

    public Stage getPlayerStage() {
        return playerStage;
    }

    public void setPlayStage(Stage stage) {
        this.playerStage = stage;

    }

    public void driverDino() {
        ((PlayerModel) model).setDriver(true);
        ((PlayerView) view).setUrlImage(PlayerView.DINO);
        ((PlayerModel) model).expandExplosionSize(5);
        ((PlayerModel) model).setPet(PlayerView.DINO);
    }

    public void driverFish() {
        ((PlayerModel) model).setDriver(true);
        ((PlayerView) view).setUrlImage(PlayerView.FISH);
        ((PlayerModel) model).speedUp();
        ((PlayerModel) model).setPet(PlayerView.FISH);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S
                || keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_A)
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

    public boolean isReverse() {
        return isReverse;
    }

    public boolean isSlide() {
        return isSlide;
    }

    public BitSet getBitSet() {
        return bitSet;
    }
}
