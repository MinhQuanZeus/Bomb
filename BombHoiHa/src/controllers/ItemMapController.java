package controllers;

import com.sun.org.apache.bcel.internal.generic.LAND;
import com.sun.org.glassfish.gmbal.GmbalException;
import models.Collision;
import models.ItemMapModel;
import models.Terrain;
import manager.GameManager;
import views.GameView;
import views.TerrainBreakView;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class ItemMapController extends GameController implements Collision {

    public ItemMapController(int x, int y, Terrain terrain, String url) {
        super(new ItemMapModel(x, y, terrain), new GameView(url));
        if (terrain != Terrain.LAND)
            GameManager.collisionManager.add(this);
    }

    public ItemMapController(int x, int y, String url, String explUrl) {
        super(new ItemMapModel(x, y, Terrain.BREAK), new TerrainBreakView(url, explUrl));
        GameManager.collisionManager.add(this);
    }

    @Override
    public void run() {
        if (view instanceof TerrainBreakView) {
            if (((TerrainBreakView) view).isFire()) {
                ((TerrainBreakView) view).explode(model);
            }
        }
    }

    @Override
    public void onContact(Collision other) {
        if (other instanceof ExplosionController) {
            if (((ItemMapModel) model).getTerrain() == Terrain.BREAK) {
                ((TerrainBreakView) view).setFire(true);
                GameManager.collisionManager.remove(this);
                GameManager.arrBlocks.remove(this);
            }
        }
    }
}
