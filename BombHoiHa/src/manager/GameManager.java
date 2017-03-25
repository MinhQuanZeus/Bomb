package manager;

import controllers.*;
import gui.GameFrame;
import gui.MainPanel;
import models.*;
import utils.Utils;
import views.AutoLoadPic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameManager {

    public static ControllerManager controllerManager;
    public static CollisionManager collisionManager;
    public static List<GameController> arrBlocks;
    public static GameController playerController;
    public static GameController secondPlayerController;
    public static ControllerManager mapManager;

    public static boolean versus;
    private static boolean transitionStart = false;
    private static boolean transitionEnd = false;
    private static boolean flag = true;
    private static int transitionFrameStart = 11;
    private static int transitionFrameEnd = 0;
    private static int transitionDelay = 0;

    public GameManager(boolean versus) {
        AutoLoadPic.init();
        this.versus = versus;
        controllerManager = new ControllerManager();
        arrBlocks = new ArrayList<>();
        collisionManager = new CollisionManager();
        if (versus) {
            playerController = new PlayerController(
                    new PlayerModel(50, 50),
                    arrBlocks,
                    "Bomberman"
            );
            secondPlayerController = new SecondPlayerController(
                    new PlayerModel(12 * ItemMapModel.SIZE_TILED, 12 * ItemMapModel.SIZE_TILED - 30),
                    arrBlocks,
                    "BombermanTwo"
            );
        } else {
            playerController = new PlayerController(
                    new PlayerModel(0, 50),
                    arrBlocks,
                    "Bomberman"
            );
        }
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
        drawInf(graphics);
    }

    public void drawInf(Graphics g) {
        if (GameManager.versus) {
            for (int i = 0; i < ((PlayerModel) playerController.getModel()).getNumberShuriken(); i++) {
                g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"), 40 + 20 * i, 5, 20, 20, null);
            }
            g.drawImage(Utils.loadImageFromRes("Bomberman/life"), 0, 0, null);
            g.drawString(((PlayerModel) playerController.getModel()).getLife() + "", 9, 20);

            for (int i = 0; i < ((PlayerModel) secondPlayerController.getModel()).getNumberShuriken(); i++) {
                g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"), GameFrame.WIDTH - 60 - 20 * i, 5, 20, 20, null);
            }
            g.drawImage(Utils.loadImageFromRes("Bomberman/life"), GameFrame.WIDTH - 35, 0, null);
            g.drawString(((PlayerModel) secondPlayerController.getModel()).getLife() + "", GameFrame.WIDTH - 26, 20);
        } else {
            for (int i = 0; i < ((PlayerModel) playerController.getModel()).getNumberShuriken(); i++) {
                g.drawImage(Utils.loadImageFromRes("Bomberman/Shuriken-3"), 140 + 20 * i, 5, 20, 20, null);
            }
            g.drawImage(Utils.loadImageFromRes("Bomberman/life"), 0, 0, null);
            g.drawImage(Utils.loadImageFromRes("Bomberman/clock"), 40, 0, null);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString("Score:" + ((PlayerModel) playerController.getModel()).getScore(), GameFrame.WIDTH - 200, 22);
            g.drawString(((PlayerModel) playerController.getModel()).getLife() + "", 9, 20);
        }
    }

    public void drawTransition(Graphics graphics) {
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
            if (transitionFrameStart == -10) {
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

    public void runTransition() {
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

    public static void setTransitionEnd(boolean transitionEnd) {
        GameManager.transitionEnd = transitionEnd;
    }
}