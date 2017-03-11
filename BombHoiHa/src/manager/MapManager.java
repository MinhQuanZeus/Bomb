package manager;

import controllers.GameController;
import controllers.ItemMapController;
import manager.ControllerManager;
import models.ItemMapModel;
import models.Terrain;
import manager.GameManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class MapManager extends ControllerManager {

    public static int mapLevel;

    public MapManager() {
        super();
        mapLevel = 1;
        readMap(mapLevel);
    }

    public void readMap(int mapLevel) {
        List<String> arrRows = readFile("resources/Map/map-" + mapLevel + "/map-" + mapLevel + ".txt");
        List<String> arrRowsTerrains = readFile("resources/Map/map-" + mapLevel + "/terrain-" + mapLevel + ".txt");
        for (int i = 0; i < arrRows.size(); i++) {
            String[] row = arrRows.get(i).split(",");
            String[] rowTerrain = arrRowsTerrains.get(i).split(",");
            for (int j = 0; j < row.length; j++) {
                int bit = Integer.parseInt(row[j] + "");
                int bitTerrain = Integer.parseInt(rowTerrain[j] + "");
                int x = j * ItemMapModel.SIZE_TILED;
                int y = i * ItemMapModel.SIZE_TILED;

                GameController itemMapController;
                Terrain terrain;
                String url = "Map/map-" + mapLevel + "/";
                if (bitTerrain == 0) {
                    terrain = Terrain.LAND;
                    itemMapController = new ItemMapController(x, y, terrain, url + bit);
                } else if (bitTerrain == 1){
                    terrain = Terrain.BLOCK;
                    itemMapController = new ItemMapController(x, y, terrain, url + bit);
                } else {
                    terrain = Terrain.BREAK;
                    itemMapController = new ItemMapController(x, y,url + bit, url + "expl");
                }

                add(itemMapController);
                if (terrain == Terrain.BLOCK || terrain == Terrain.BREAK) {
                    GameManager.arrBlocks.add(itemMapController);
                }
            }
        }
    }

    private List<String> readFile(String url) {
        try {
            List<String> arrRows = new ArrayList<>();
            File file = new File(url);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String row = bufferedReader.readLine();
            while (row != null) {
                arrRows.add(row);
                row = bufferedReader.readLine();
            }
            bufferedReader.close();
            return arrRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
