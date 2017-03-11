package models;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class ItemMapModel extends GameModel {

    public static final int SIZE_TILED = 50;

    private Terrain terrain;

    public ItemMapModel(int x, int y, Terrain terrain) {
        super(x, y, SIZE_TILED, SIZE_TILED);
        this.terrain = terrain;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
