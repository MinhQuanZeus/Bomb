package controllers;

import controllers.enemy_weapon.ShotDirection;
import gui.GameFrame;
import models.PlayerModel;
import models.ShurikenModel;
import utils.Utils;
import views.PlayerView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by KhoaBeo on 3/25/2017.
 */
public class PlayerTwoController extends PlayerController {

    public PlayerTwoController(PlayerModel model, List<GameController> arrBlocks, String urlImage) {
        super(model, arrBlocks, urlImage);
    }

    @Override
    public void draw(Graphics g) {
        view.draw(g, model);
        for (int i = 0; i < ((PlayerModel) model).getNumberShuriken(); i++) {
            g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"), GameFrame.WIDTH - 60 - 20 * i, 5, 20, 20, null);
        }
        g.drawImage(Utils.loadImageFromRes("Bomberman/life"), GameFrame.WIDTH - 35, 0, null);
        g.drawString(((PlayerModel) model).getLife() + "", GameFrame.WIDTH - 26, 20);
    }

    @Override
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
                if (bitSet.get(KeyEvent.VK_DOWN)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_UP)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_LEFT)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = model.getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -model.getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
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
            if (bitSet.get(KeyEvent.VK_NUMPAD0)) {
                bombard();
            }
            if (bitSet.get(KeyEvent.VK_NUMPAD1)) {
                if (((PlayerModel) model).getNumberShuriken() > 0 && reloadShuriken > RELOAL_SHURIKEN_SPEED) {
                    ShurikenController shurikenController = ShurikenController.create(model.getX() + model.getWidth() / 2 - ShurikenModel.WIDTH / 2, model.getY() + model.getHeight() / 2, ((PlayerModel) model).getShotDirection());
                    reloadShuriken = 0;
                    ((PlayerModel) model).decreaseNumberShuriken();
                }
            }
        } else {
            view.explode(model);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
            bitSet.clear();
        bitSet.set(e.getKeyCode());
    }
}
