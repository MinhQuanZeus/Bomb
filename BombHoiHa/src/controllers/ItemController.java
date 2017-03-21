package controllers;

import manager.GameManager;
import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import utils.Utils;
import views.ItemView;

/**
 * Created by QuanT on 3/12/2017.
 */
public class ItemController extends GameController implements Collision {
    public static final int WIDTH = ItemMapModel.SIZE_TILED;
    public static final int HEIGHT = ItemMapModel.SIZE_TILED;
    private ItemType type;

    public ItemController(GameModel model, ItemView view, ItemType itemType) {
        super(model, view);
        this.type = itemType;
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
    }

    @Override
    public void onContact(Collision other) {
        if(other instanceof PlayerController){
            Utils.playSound("item-get.wav",false);
            model.setAlive(false);
        }
        if(other instanceof ExplosionController){
            model.setAlive(false);
        }
    }

    public static void create(int x, int y, ItemType type) {
        new ItemController(
                new GameModel(x, y, WIDTH, HEIGHT),
                new ItemView("Items/"+type),
                type
        );
    }

    public ItemType getType() {
        return type;
    }
}
