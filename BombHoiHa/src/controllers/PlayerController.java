package controllers;

import controllers.enemy_weapon.BulletController;
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
        if (!((PlayerModel) model).isExplode()) {
            ((PlayerModel) model).checkImmunity();
            model.move(vector, arrBlocks);
            this.vector.dx = 0;
            this.vector.dy = 0;

            if (bitSet.get(KeyEvent.VK_DOWN)) {
                view.setImage(PlayerView.MOVE_DOWN);
                this.vector.dy = ((PlayerModel) model).getSpeed();
            } else if (bitSet.get(KeyEvent.VK_UP)) {
                view.setImage(PlayerView.MOVE_UP);
                this.vector.dy = -((PlayerModel) model).getSpeed();
            } else if (bitSet.get(KeyEvent.VK_LEFT)) {
                view.setImage(PlayerView.MOVE_LEFT);
                this.vector.dx = -((PlayerModel) model).getSpeed();
            } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
                view.setImage(PlayerView.MOVE_RIGHT);
                this.vector.dx = ((PlayerModel) model).getSpeed();
            } else {
                view.setImageHold();
            }
            if (bitSet.get(KeyEvent.VK_SPACE)) {
                bombard();
            }
        } else {
            view.explode(model);
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
            Utils.playSound("bomb-set.wav",false);
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
            }
        }

        if (!((PlayerModel) model).isImmunity()) {
            if (other instanceof ExplosionController) {
                Rectangle rectangle = model.getIntersectionRect(((ExplosionController) other).model);
                if (rectangle.getWidth() > 10 && rectangle.getHeight() > 10) {
                    if (!((PlayerModel) model).isExplode())
                        Utils.playSound("player-out.wav", false);
                    ((PlayerModel) model).setExplode(true);
                }
            }

            if (other instanceof EnemyController) {
                EnemyModel enemyModel = (EnemyModel) other.getModel();
                if (enemyModel.getBottomRect(enemyModel.getX(), enemyModel.getY()).intersects(model.getBottomRect(model.getX(), model.getY()))) {
                    if (enemyModel.getHp() != 0) {
                        if (!((PlayerModel) model).isExplode())
                            Utils.playSound("player-out.wav", false);
                        ((PlayerModel) model).setExplode(true);
                    }
                }
            }

            if (other instanceof BulletController) {
                ((PlayerModel) model).setExplode(true);
            }
        }
    }
}
