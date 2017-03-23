package manager;

import controllers.*;
import gui.GameFrame;
import gui.GamePanel;
import models.ItemMapModel;
import models.PlayerModel;
import models.Terrain;
import utils.Utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KhoaBeo on 3/10/2017.
 */
public class MapManager extends ControllerManager {

    public static final int LEVEL_MAX = 2;
    public static int[][] map;
    public static int mapLevel;

    private static long exist;
    private long start;

    private static boolean isCountTime = true;
    private static final int TIME_BOUNOUS = 15;
    private long currentTime = 0;

    public MapManager() {
        super();
        mapLevel = 3;
        map = new int[14][14];
        readMap(mapLevel);
        exist = 180000;
        start = System.currentTimeMillis();
    }

    public void changeMap(int level) {
        mapLevel = level;
        GameManager.arrBlocks.clear();
        readMap(mapLevel);
        start = System.currentTimeMillis();
    }

    private String getCurrentTime() {

        if(isCountTime) {
            currentTime = (exist - System.currentTimeMillis() + start) / 1000;
        }else{
            exist+=(exist - System.currentTimeMillis() + start) / (1000*17);
        }
        long minutes = currentTime / 60;
        long seconds = currentTime % 60;
        return minutes + ":" + seconds;
    }

    public static void setCountTime(boolean countTime) {
        isCountTime = countTime;
    }

    public static void bounousTime(){
        exist += TIME_BOUNOUS*1000;
    }

    @Override
    public void run() {
        super.run();
        if (getCurrentTime().equals("0:0")) {
            GameFrame.mainPanel.showPanel(false);
            GamePanel.running = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.white);
        g.drawString(getCurrentTime(),20,  20);
        for(int i = 0;i< PlayerController.numberShuriken;i++){
            g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"),80+20*i,5,20,20,null);
        }
    }

    private void readMap(int mapLevel) {
        List<String> arrRows = readFile("resources/Map/map-" + mapLevel + "/map-" + mapLevel + ".txt");
        List<String> arrRowsTerrains = readFile("resources/Map/map-" + mapLevel + "/terrain-" + mapLevel + ".txt");
        List<String> arrRowsEnemy = readFile("resources/Map/map-" + mapLevel + "/mapEnemy-" + mapLevel + ".txt");
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
                ItemController itemController = null;
                EnemyController enemyController = null;
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

                enemyController = EnemyController.createByRow_Colum_Number(bitEnemy,i,j,(PlayerModel)GameManager.playerController.getModel());
                if(enemyController != null){
                    add(enemyController);
                }
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
