package controllers;

import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import models.Terrain;
import manager.GameManager;
import views.ExplosionView;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class ExplosionController extends GameController implements Collision {

    public ExplosionController(int x, int y, String url) {
        super(
                new GameModel(x, y, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED),
                new ExplosionView(url)
                );
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ItemMapController) {
            if (((ItemMapModel) other.getModel()).getTerrain() != Terrain.LAND) {
                model.setAlive(false);
            }
        }
    }
}
