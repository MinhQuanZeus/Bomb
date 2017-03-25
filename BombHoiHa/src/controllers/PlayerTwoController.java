package controllers;
import controllers.enemy_weapon.ShotDirection;
import models.PlayerModel;
import models.ShurikenModel;
import views.GameView;
import views.PlayerView;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Created by KhoaBeo on 3/25/2017.
 */
public class PlayerTwoController extends PlayerController {

    public PlayerTwoController(PlayerModel model, GameView view, List<GameController> arrBlocks) {
        super(model, view, arrBlocks);
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
                if (bitSet.get(KeyEvent.VK_S)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_W)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.DOWN);
                        view.setImage(PlayerView.MOVE_DOWN);
                        this.vector.dy = ((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.UP);
                        view.setImage(PlayerView.MOVE_UP);
                        this.vector.dy = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_A)) {
                    if (isReverse) {
                        ((PlayerModel) model).setShotDirection(ShotDirection.RIGHT);
                        view.setImage(PlayerView.MOVE_RIGHT);
                        this.vector.dx = ((PlayerModel) model).getSpeed();
                    } else {
                        ((PlayerModel) model).setShotDirection(ShotDirection.LEFT);
                        view.setImage(PlayerView.MOVE_LEFT);
                        this.vector.dx = -((PlayerModel) model).getSpeed();
                    }
                } else if (bitSet.get(KeyEvent.VK_D)) {
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
                if (numberShuriken > 0 && reloadShuriken > RELOAL_SHURIKEN_SPEED) {
                    ShurikenController shurikenController = ShurikenController.create(model.getX() + model.getWidth() / 2 - ShurikenModel.WIDTH / 2, model.getY() + model.getHeight() / 2, ((PlayerModel) model).getShotDirection());
                    reloadShuriken = 0;
                    ((PlayerModel) model).decreaseNumberShuriken();
                }
            }
        } else {
            view.explode(model);
        }
    }
}
