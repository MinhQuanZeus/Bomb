package manager;

import controllers.EnemyController;
import controllers.GameController;
import controllers.ItemMapController;
import controllers.*;
import gui.GameFrame;
import models.EnemyModel;
import models.ItemMapModel;
import models.PlayerModel;
import models.Terrain;
import utils.Utils;
import gui.MainPanel;
import views.AnimationView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class MapManager extends ControllerManager {

    public static final int LEVEL_MAX = 3;
    public static int[][] map;
    public static int mapLevel;
    private static long exist;
    private ItemMapController portalItem;
    private long start;
    private ImageIcon winIcon;


    public static boolean isCountTime = true;
    private static final int TIME_BONUS = 15;

    private long currentTime = 0;

    public MapManager() {
        super();
        mapLevel = 1;
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
        winIcon = new ImageIcon("resources/System/win.png");
    }

    public void changeMap(int level) {
        System.out.println("changeMap");
        mapLevel = level;
        gameControllers.remove(portalItem);
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
            } else if (mapLevel >= LEVEL_MAX) {
                MainPanel.gamePanel.addTitle(winIcon);
            }
        }
    }

    private String getCurrentTime() {
        if (isCountTime) {
            currentTime = (exist - System.currentTimeMillis() + start) / 1000;
        } else {
            exist += (exist - System.currentTimeMillis() + start) / (1000 * 17);
        }
        long minutes = currentTime / 60;
        long seconds = currentTime % 60;
        return minutes + ":" + ((seconds < 10) ? ("0" + seconds) : seconds);
    }

    public static void setCountTime(boolean countTime) {
        isCountTime = countTime;
    }

    public static void bonusTime() {
        exist += TIME_BONUS * 1000;
    }

    @Override
    public void run() {
        super.run();
        if (getCurrentTime().equals("0:00")) {
            MainPanel.gamePanel.addTitle(new ImageIcon("resources/System/time-up.png"));
        }
        checkLevelClear();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.white);
        for (int i = 0; i < ((PlayerModel) ((PlayerController) GameManager.playerController).getModel()).getNumberShuriken(); i++) {
            g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"), 140 + 20 * i, 5, 20, 20, null);
        }
        if ((getCurrentTime().contains("0:0") || getCurrentTime().contains("0:1") || getCurrentTime().contains("0:2"))
                && System.currentTimeMillis() % 2 == 0)
            g.setColor(Color.RED);
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

                //EnemyController.createByRow_Colum_Number(bitEnemy, i, j, (PlayerModel) GameManager.playerController.getModel());

                if (terrain == Terrain.BLOCK || terrain == Terrain.BREAK) {
                    GameManager.arrBlocks.add(itemMapController);
                }
            }
        }
        EnemyController.createByRow_Colum_Number(20, 5, 5, (PlayerModel) GameManager.playerController.getModel());
    }

    public void reloadStart(long offset) {
        this.start += offset;
    }
}
