package manager;

import controllers.*;
import gui.GameFrame;
import gui.MainPanel;
import models.*;
import sun.applet.Main;
import utils.Utils;
import views.AnimationView;
import views.AutoLoadPic;
import views.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameManager {

    public static ControllerManager controllerManager;
    public static CollisionManager collisionManager;
    public static List<GameController> arrBlocks;
    public static GameController playerController;
    public static ControllerManager mapManager;

    private static boolean transitionStart = false;
    private static boolean transitionEnd = false;
    private static boolean flag = true;
    private static int transitionFrameStart = 11;
    private static int transitionFrameEnd = 0;
    private static int transitionDelay = 0;

    public GameManager() {
        AutoLoadPic.init();
        controllerManager = new ControllerManager();
        arrBlocks = new ArrayList<>();
        collisionManager = new CollisionManager();
        playerController = new PlayerController(
                new PlayerModel(0, 50),
                new PlayerView(),
                arrBlocks
        );
        mapManager = new MapManager();
    }

    public void run() {
        mapManager.run();
        controllerManager.run();
        collisionManager.run();
        runTransition();
    }

    public void draw(Graphics graphics) {
        mapManager.draw(graphics);
        controllerManager.draw(graphics);
        drawTransition(graphics);
    }

    public void drawTransition(Graphics graphics){
        if (transitionStart) {
            if (transitionFrameStart < 0) {
                graphics.drawImage(Utils.loadImageFromRes("System/transition-" + 0), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
            } else {
                graphics.drawImage(Utils.loadImageFromRes("System/transition-" + transitionFrameStart), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
            }
            if (transitionDelay < 1)
                transitionDelay++;
            if (transitionDelay == 1) {
                transitionFrameStart--;
                transitionDelay = 0;
            }
            if(transitionFrameStart==-10){
                transitionEnd = true;
                transitionStart = false;
            }
        }

        if (transitionEnd) {
            graphics.drawImage(Utils.loadImageFromRes("System/transition-" + transitionFrameEnd), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
            if (transitionDelay < 1)
                transitionDelay++;
            if (transitionDelay == 1) {
                transitionFrameEnd++;
                transitionDelay = 0;
            }
            if (transitionFrameEnd > 11) {
                transitionFrameEnd = 0;
                transitionFrameStart = 11;
                transitionDelay = 0;
                transitionEnd = false;
                flag = true;
                MainPanel.gamePanel.addTitle(new ImageIcon("resources/System/stage-" + MapManager.mapLevel + ".png"));
            }
        }
    }

    public void runTransition(){
        if (transitionFrameStart == 0 && flag == true) {
            ((MapManager) GameManager.mapManager).changeMap(MapManager.mapLevel + 1);
            playerController.getModel().setX(0);
            playerController.getModel().setY(50);
            flag = false;
        }
    }

    public static void setTransitionStart(boolean transitionStart) {
        GameManager.transitionStart = transitionStart;
    }
}