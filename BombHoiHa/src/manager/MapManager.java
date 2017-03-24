package manager;

import controllers.EnemyController;
import controllers.GameController;
import controllers.ItemMapController;
import gui.GameFrame;
import models.*;
import gui.MainPanel;
import utils.Utils;
import views.AnimationView;

import java.awt.*;
import java.util.List;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class MapManager extends ControllerManager {

    public static final int LEVEL_MAX = 3;
    public static int[][] map;
    public static int mapLevel;

    private ItemMapController portalItem;
    private long exist;
    private long start;

    public MapManager() {
        super();
        mapLevel = 3;
        map = new int[14][14];
        readMap(mapLevel);
        exist = 120000;
        start = System.currentTimeMillis();
        portalItem = new ItemMapController(
                0,
                0,
                Terrain.CHANGE_MAP,
                new AnimationView("Portal/portal", 4)
        );
    }

    public void changeMap(int level) {
        mapLevel = level;
        gameControllers.remove(portalItem);
        GameManager.collisionManager.remove(portalItem);
        GameManager.arrBlocks.clear();
        GameManager.controllerManager.clear();
        this.clear();
        readMap(mapLevel);
        start = exist + start;
        MainPanel.setBGM(MainPanel.TAG_GAME);
    }

    private void checkLevelClear() {
        if (EnemyModel.enemyCount == 0) {
            if (mapLevel < LEVEL_MAX) {
                int x;
                int y;

                do {
                    x = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                    y = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                } while (MapManager.map[Utils.getRowMatrix(y)][Utils.getColMatrix(x)] != 0);

                if (!gameControllers.contains(portalItem)) {
                    gameControllers.add(portalItem);
                    GameManager.collisionManager.add(portalItem);
                    portalItem.getModel().setX(x);
                    portalItem.getModel().setY(y);
                }
            } else {
                GameFrame.mainPanel.showEndPanel(true);
            }
        }
    }

    private String getCurrentTime() {
        long currentTime = (exist - System.currentTimeMillis() + start) / 1000;
        long minutes = currentTime / 60;
        long seconds = currentTime % 60;
        return minutes + ":" + ((seconds < 10) ? ("0" + seconds) : seconds);
    }

    @Override
    public void run() {
        super.run();
        if (getCurrentTime().equals("0:00")) {
            GameFrame.mainPanel.showEndPanel(false);
        }
        checkLevelClear();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.white);
        g.drawString(getCurrentTime(), 80, 22);
    }

    private void readMap(int mapLevel) {
        List<String> arrRows = Utils.readFileMap("resources/Map/map-" + mapLevel + "/map-" + mapLevel + ".txt");
        List<String> arrRowsTerrains = Utils.readFileMap("resources/Map/map-" + mapLevel + "/terrain-" + mapLevel + ".txt");
        List<String> arrRowsEnemy = Utils.readFileMap("resources/Map/map-" + mapLevel + "/mapEnemy-" + mapLevel + ".txt");
        for (int i = 0; i < arrRows.size(); i++) {
            String[] row = arrRows.get(i).split(",");
            String[] rowTerrain = arrRowsTerrains.get(i).split(",");
            String[] rowEnemy = arrRowsEnemy.get(i).split(",");
            for (int j = 0; j < row.length; j++) {
                int bit = Integer.parseInt(row[j] + "");
                int bitTerrain = Integer.parseInt(rowTerrain[j] + "");
                int bitEnemy = Integer.parseInt(rowEnemy[j] + "");

                map[i][j] = bitTerrain;

                int x = j * ItemMapModel.SIZE_TILED;
                int y = i * ItemMapModel.SIZE_TILED;
                GameController itemMapController;
                Terrain terrain;
                String url = "Map/map-" + mapLevel + "/";
                if (bitTerrain == 0) {
                    terrain = Terrain.LAND;
                    itemMapController = new ItemMapController(x, y, terrain, url + bit);
                } else if (bitTerrain == 1) {
                    terrain = Terrain.BLOCK;
                    itemMapController = new ItemMapController(x, y, terrain, url + bit);
                } else {
                    terrain = Terrain.BREAK;
                    itemMapController = new ItemMapController(x, y, url + bit, url + "expl");

                }
                add(itemMapController);

                EnemyController.createByRow_Colum_Number(bitEnemy, i, j, (PlayerModel) GameManager.playerController.getModel());

                if (terrain == Terrain.BLOCK || terrain == Terrain.BREAK) {
                    GameManager.arrBlocks.add(itemMapController);
                }
            }
        }
    }

    public void reloadStart(long offset) {
        this.start += offset;
    }
}
