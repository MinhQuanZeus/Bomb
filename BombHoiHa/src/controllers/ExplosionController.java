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

    // 2 la nguoi choi
    private int eplosionFrom = 1;
    public ExplosionController(int x, int y, String url,int eplosionFrom) {
        super(
                new GameModel(x, y, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED),
                new ExplosionView(url)
                );
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);

        this.eplosionFrom = eplosionFrom;
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ItemMapController) {
            if (((ItemMapModel) other.getModel()).getTerrain() != Terrain.LAND) {
                model.setAlive(false);
            }
        }
    }

    public int getFrom() {
        return eplosionFrom;
    }
}
