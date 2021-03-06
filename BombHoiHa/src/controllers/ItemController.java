package controllers;

import manager.GameManager;
import manager.MapManager;
import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import utils.Utils;
import views.ItemView;
import views.PlayerView;

import java.awt.*;

/**
 * Created by QuanT on 3/12/2017.
 */
public class ItemController extends GameController implements Collision {
    public static final int WIDTH = ItemMapModel.SIZE_TILED;
    public static final int HEIGHT = ItemMapModel.SIZE_TILED;
    public static final int MAX_KICK_TIME = 1000;
    private ItemType type;
    private int countDown = 4;

    int count = 0;

    public ItemController(GameModel model, ItemView view, ItemType itemType) {
        super(model, view);
        type = itemType;
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof PlayerController) {
            model.setAlive(false);
            PlayerModel playerModel = (PlayerModel) other.getModel();
            switch (type) {
                case EGG:
                    if (playerModel.isDriver()) {
                        if (playerModel.getPet().equals(PlayerView.DINO)) {
                            model.setAlive(true);
                            return;
                        } else {
                            playerModel.getOutFish();
                            ((PlayerController) other).driverDino();
                        }
                    } else {
                        ((PlayerController) other).driverDino();
                    }
                    break;
                case EGGFISH:
                    if (playerModel.isDriver()) {
                        if (playerModel.getPet().equals(PlayerView.FISH)) {
                            model.setAlive(true);
                            return;
                        } else {
                            playerModel.expandExplosionSize(-5);
                            ((PlayerController) other).driverFish();
                        }
                    } else {
                        ((PlayerController) other).driverFish();
                    }
                    break;
                case BONUS_TIME:
                    MapManager.bonusTime();
                    break;
                case SPEED_UP:
                    playerModel.speedUp();
                    break;
                case EXPAND_EXPLOSIVE:
                    playerModel.expandExplosionSize();
                    break;
                case EXPAND_BOMB:
                    playerModel.expandMaxBomb();
                    break;
                case FREEZE:
                    if(GameManager.versus){
                        if (other instanceof SecondPlayerController) {
                            ((PlayerController) GameManager.playerController).setPlayStage(Stage.FREEZE);
                        } else {
                            ((PlayerController) GameManager.secondPlayerController).setPlayStage(Stage.FREEZE);
                        }
                    }else {
                        GameManager.controllerManager.freeze();
                        MapManager.setCountTime(false);
                    }
                    break;
                case SHURIKEN:
                    playerModel.bonusShuriken();
                    break;
                case BONUS_LIFE:
                    playerModel.bonusLife();
                    break;
                case SLIDE:
                    if (GameManager.versus) {
                        if (other instanceof SecondPlayerController) {
                            ((PlayerController) GameManager.playerController).setSlide();
                        } else {
                            ((PlayerController) GameManager.secondPlayerController).setSlide();
                        }
                    } else {
                        ((PlayerController) other).setSlide();
                    }
                    break;
                case REVERSE_MOVE:
                    if (GameManager.versus) {
                        if (other instanceof SecondPlayerController) {
                            ((PlayerController) GameManager.playerController).reverseMove();
                        } else {
                            ((PlayerController) GameManager.secondPlayerController).reverseMove();
                        }
                    } else {
                        ((PlayerController) other).reverseMove();
                    }
                    break;
                case DIE:
                    if (GameManager.versus) {
                        if (other instanceof SecondPlayerController) {
                            ((PlayerModel) GameManager.playerController.getModel()).setExplode(true);
                        } else {
                            ((PlayerModel) GameManager.secondPlayerController.getModel()).setExplode(true);
                        }
                    } else {
                        playerModel.setExplode(true);
                    }
                    break;
                case SPIDERWEB:
                    if (GameManager.versus) {
                        if (other instanceof SecondPlayerController) {
                            ((PlayerModel) GameManager.playerController.getModel()).speedDown();
                        } else {
                            ((PlayerModel) GameManager.secondPlayerController.getModel()).speedDown();
                        }
                    } else {
                        playerModel.speedDown();
                    }
                    break;
                case KICK:
                    playerModel.setKick(true);
                    ((PlayerController)other).resetCountDownKickPlayer();
                    break;
            }
            Utils.playSound("item-get.wav", false);
        }
        if (other instanceof ExplosionController) {
            model.setAlive(false);
        }
    }

    public static void create(int x, int y) {
        ItemType type;
        do {
            type = ItemType.getRandomItemType();
        } while (GameManager.versus && (type == ItemType.BONUS_TIME || type == ItemType.DIE));

        if (type == ItemType.EGG || type == ItemType.EGGFISH) {
            new ItemController(
                    new GameModel(x, y, WIDTH, HEIGHT),
                    new ItemView("Items/" + type, 9),
                    type
            );
        } else {
            new ItemController(
                    new GameModel(x, y, WIDTH, HEIGHT),
                    new ItemView("Items/" + type, 2),
                    type
            );
        }
    }

    @Override
    public void run() {
        super.run();
        count++;
        if (count == (1000 / 17) && countDown > 0) {
            countDown--;
            count = 0;
        }
        if (countDown == 0 && !GameManager.versus) {
            PlayerController playerController = (PlayerController) GameManager.playerController;
            switch (type) {
                case SLIDE:
                    playerController.setSlide();
                    model.setAlive(false);
                    break;
                case REVERSE_MOVE:
                    playerController.reverseMove();
                    model.setAlive(false);
                    break;
                case DIE:
                    playerController.die();
                    model.setAlive(false);
                    break;
                case SPIDERWEB:
                    playerController.speedDown();
                    model.setAlive(false);
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.white);

        if (!GameManager.versus) {
            if (type == ItemType.REVERSE_MOVE || type == ItemType.DIE || type == ItemType.SLIDE || type == ItemType.SPIDERWEB) {
                g.drawString(countDown + "", model.getX(), model.getY());
            }
        }
    }

    public ItemType getType() {
        return type;
    }
}
