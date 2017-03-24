package controllers;

import manager.GameManager;
import manager.MapManager;
import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import utils.Utils;
import views.ItemView;

import java.awt.*;

/**
 * Created by QuanT on 3/12/2017.
 */
public class ItemController extends GameController implements Collision {
    public static final int WIDTH = ItemMapModel.SIZE_TILED;
    public static final int HEIGHT = ItemMapModel.SIZE_TILED;
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
            Utils.playSound("item-get.wav", false);
            model.setAlive(false);
            PlayerModel playerModel = (PlayerModel) ((PlayerController) other).getModel();
            switch (type) {
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
                    GameManager.controllerManager.freeze();
                    MapManager.setCountTime(false);
                    break;
                case SHURIKEN:
                    playerModel.bonusShuriken();
                    break;
                case BONUS_LIFE:
                    playerModel.bonusLife();
                    break;
//                case SLIDE:
//                    PlayerController.setSlide();
//                    model.setAlive(false);
//                    break;
//                case REVERSE_MOVE:
//                    PlayerController.reverseMove();
//                    break;
//                case DIE:
//                    playerModel.setExplode(true);
//                    break;
//                case SPIDERWEB:
//                    playerModel.speedDown();
//                    break;
//                case KICK:
//                    playerModel.setKick(true);
//                    break;

            }
        }
        if (other instanceof ExplosionController) {
            model.setAlive(false);
        }
    }

    public static void create(int x, int y) {
        ItemType type = ItemType.getRandomItemType();
        new ItemController(
                new GameModel(x, y, WIDTH, HEIGHT),
                new ItemView("Items/" + type),
                type
        );
    }

    @Override
    public void run() {
        super.run();
        count++;
        if (count == (1000 / 17) && countDown > 0) {
            countDown--;
            count = 0;
        }
        if (countDown == 0) {
            switch (type) {
                case SLIDE:
                    PlayerController.setSlide();
                    model.setAlive(false);
                    break;
                case REVERSE_MOVE:
                    PlayerController.reverseMove();
                    model.setAlive(false);
                    break;
                case DIE:
                    PlayerController.die();
                    model.setAlive(false);
                    break;
                case SPIDERWEB:
                    PlayerController.speedDown();
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
        if (type == ItemType.REVERSE_MOVE || type == ItemType.DIE || type == ItemType.SLIDE || type == ItemType.SPIDERWEB) {
            g.drawString(countDown + "", model.getX(), model.getY());
        }
    }

    public ItemType getType() {
        return type;
    }
}
