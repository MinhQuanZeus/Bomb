package views;

import controllers.ItemController;
import controllers.ItemType;
import manager.MapManager;
import models.GameModel;
import models.ItemMapModel;
import models.Terrain;
import utils.Utils;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/12/2017.
 */
public class TerrainBreakView extends GameView {

    private Animation explosion;
    private boolean fire;

    public TerrainBreakView(String url, String epxlUrl) {
        super(url);
        explosion = new Animation(100, 6, epxlUrl);
    }

    public void explode(GameModel model) {
        if (explosion.getImage() != null) {
            image = explosion.getImage();
        } else {
            image = Utils.loadImageFromRes("Map/map-" + MapManager.mapLevel + "/0");
            ((ItemMapModel) model).setTerrain(Terrain.LAND);
            fire = false;
      //      if (Utils.getRandom(3) == 0) {
                ItemController.create(model.getX(), model.getY());
      //      }
        }
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }
}

