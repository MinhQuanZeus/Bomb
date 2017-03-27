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
    private static int transitionFrameStart;
    private static int transitionFrameEnd;
    private static int transitionDelay;
    private int countDownRandomItem;
    private boolean flag;

    public GameManager(boolean versus, int stage) {
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
        countDownRandomItem = 0;
        flag = true;
        if (versus) {
            playerController = new PlayerController(
                    new PlayerModel(0, 0),
                    arrBlocks,
                    "Bomberman",
                    new PlayerFreezeBehavior(250)
            );

            secondPlayerController = new SecondPlayerController(
                    new PlayerModel(0, 0),
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
        mapManager = new MapManager(stage);
        if (versus) {
            int x;
            int y;

            do {
                x = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                y = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
            } while (MapManager.map[Utils.getRowMatrix(y)][Utils.getColMatrix(x)] != 0);

            playerController.getModel().setX(x);
            playerController.getModel().setY(y - 20);

            do {
                x = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                y = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
            } while (MapManager.map[Utils.getRowMatrix(y)][Utils.getColMatrix(x)] != 0);

            secondPlayerController.getModel().setX(x);
            secondPlayerController.getModel().setY(y - 20);
        }
    }

    public void run() {
        mapManager.run();
        controllerManager.run();
        collisionManager.run();
        randomAddItem();
        runTransition();
    }

    public void draw(Graphics graphics) {
        mapManager.draw(graphics);
        controllerManager.draw(graphics);
        drawTransition(graphics);
        drawInf(graphics);
    }

    public void randomAddItem() {
        if (versus || MapManager.mapLevel == MapManager.LEVEL_MAX) {
            countDownRandomItem++;
            if (countDownRandomItem == 1800) {
                int x;
                int y;

                do {
                    x = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                    y = Utils.getRandom(14) * ItemMapModel.SIZE_TILED;
                } while (MapManager.map[Utils.getRowMatrix(y)][Utils.getColMatrix(x)] != 0);

                ItemController.create(x, y);
                countDownRandomItem = 0;
            }
        }
    }

    public void drawInf(Graphics g) {
        if (GameManager.versus) {
            drawAbility(g, playerController, 0);
            drawAbility(g, secondPlayerController, GameFrame.WIDTH - 33);
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

        graphics.drawImage(Utils.loadImageFromRes("Items/EXPAND_BOMB-1"), x, y, 30, 30, null);
        graphics.drawString(model.getMaxBomb() + "", x, y + 15);
        y += 40;

        graphics.drawImage(Utils.loadImageFromRes("Items/EXPAND_EXPLOSIVE-1"), x, y, 30, 30, null);
        graphics.drawString(model.getExplosionSize() + "", x, y + 15);
        y += 40;

        graphics.drawImage(Utils.loadImageFromRes("Items/SPEED_UP-1"), x, y, 30, 30, null);
        graphics.drawString(model.getSpeed() + "", x, y + 15);
        y += 40;

        if (model.isKick()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/KICK-1"), x, y, 30, 30, null);
            y += 40;
        }

        if (model.getCountDownSlow() > 0 ) {
            graphics.drawImage(Utils.loadImageFromRes("Items/SPIDERWEB-1"), x, y, 30, 30, null);
            y += 40;
        }

        if (((PlayerController) gameController).isReverse()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/REVERSE_MOVE-1"), x, y, 30, 30, null);
            y += 40;
        }

        if (((PlayerController) gameController).isSlide()) {
            graphics.drawImage(Utils.loadImageFromRes("Items/SLIDE-1"), x, y, 30, 30, null);
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
                if (versus) {
                    MainPanel.gamePanel.addTitle(new ImageIcon("resources/System/stage-0.png"));
                } else {
                    MainPanel.gamePanel.addTitle(new ImageIcon("resources/System/stage-" + MapManager.mapLevel + ".png"));
                }
            }
        }
    }

    public void runTransition() {
        if (transitionFrameStart == 0 && flag) {
            ((MapManager) GameManager.mapManager).changeMap(MapManager.mapLevel + 1);
            playerController.getModel().setX(0);
            playerController.getModel().setY(50);

            if(MapManager.mapLevel == MapManager.LEVEL_MAX){
                playerController.getModel().setX(6*ItemMapModel.SIZE_TILED);
                playerController.getModel().setY(10*ItemMapModel.SIZE_TILED);
            }
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