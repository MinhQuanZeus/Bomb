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
    public static ControllerManager mapManager;
    public static CollisionManager collisionManager;
    public static List<GameController> arrBlocks;
    public static GameController playerController;
    public static GameController secondPlayerController;
    public static boolean versus;

    private static boolean transitionStart;
    private static boolean transitionEnd;
    private static boolean flag;
    private static int transitionFrameStart;
    private static int transitionFrameEnd;
    private static int transitionDelay;

    public GameManager(boolean versus) {
        AutoLoadPic.init();
        this.versus = versus;
        controllerManager = new ControllerManager();
        arrBlocks = new ArrayList<>();
        collisionManager = new CollisionManager();
        transitionStart = false;
        transitionEnd = false;
        transitionFrameStart = 11;
        transitionFrameEnd = 0;
        transitionDelay = 0;
        flag = true;
        if (versus) {
            playerController = new PlayerController(
                    new PlayerModel(50, 50),
                    arrBlocks,
                    "Bomberman",
                    new PlayerFreezeBehavior(250)
            );
            secondPlayerController = new SecondPlayerController(
                    new PlayerModel(12 * ItemMapModel.SIZE_TILED, 12 * ItemMapModel.SIZE_TILED - 30),
                    arrBlocks,
                    "BombermanTwo",
                    new PlayerFreezeBehavior(250)
            );
        } else {
            playerController = new PlayerController(
                    new PlayerModel(5, 50),
                    arrBlocks,
                    "Bomberman",
                    new PlayerFreezeBehavior(250)
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
            drawAbility(g, playerController, 0);
            drawAbility(g, secondPlayerController, GameFrame.WIDTH - 30);
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
            drawAbility(g, playerController, 0);
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

    public void drawAbility(Graphics graphics, GameController gameController, int x) {
        PlayerModel model = (PlayerModel) gameController.getModel();
        ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

        int y = 200;
        graphics.setFont(new Font("Courier New", Font.BOLD, 20));
        graphics.setColor(Color.white);

        graphics.drawImage(Utils.loadImageFromRes("Items/EXPAND_BOMB-0"), x, y, 30, 30, null);
        graphics.drawString(model.getMaxBomb() + "", x, y + 15);
        y += 40;

        graphics.drawImage(Utils.loadImageFromRes("Items/EXPAND_EXPLOSIVE-0"), x, y, 30, 30, null);
        graphics.drawString(model.getExplosionSize() + "", x, y + 15);
        y += 40;

        graphics.drawImage(Utils.loadImageFromRes("Items/SPEED_UP-0"), x, y, 30, 30, null);
        graphics.drawString(model.getSpeed() + "", x, y + 15);
        y += 40;

        if (model.isKick()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/KICK-0"), x, y, 30, 30, null);
            y += 40;
        }

        if (((PlayerController) gameController).isReverse()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/REVERSE_MOVE-0"), x, y, 30, 30, null);
            y += 40;
        }

        if (((PlayerController) gameController).isSlide()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/SLIDE-0"), x, y, 30, 30, null);
        }

        ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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