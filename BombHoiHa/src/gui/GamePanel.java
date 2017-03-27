package gui;

import controllers.PlayerController;
import manager.GameManager;
import manager.MapManager;
import models.PlayerModel;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GamePanel extends JPanel implements Runnable {

    public static boolean paused;
    public static final ImageIcon winIcon = new ImageIcon("resources/System/win.png");
    public static final ImageIcon clearIcon = new ImageIcon("resources/System/stage-clear.png");
    private static boolean flag;

    private boolean running;
    private Thread thread;
    private GameManager gameManager;
    private PausedPanel pausedPanel;
    private long startPaused;
    private JLabel title;
    private int titleExist;

    public GamePanel(boolean versus, int stage) {
        setLayout(null);
        setFocusable(true);
        gameManager = new GameManager(versus, stage);

        paused = false;
        flag = true;

        title = new JLabel();
        addKeyListener((KeyListener) GameManager.playerController);
        if (versus)
            addKeyListener((KeyListener) GameManager.secondPlayerController);

        pausedPanel = new PausedPanel(this);
        add(pausedPanel);

        running = true;
        thread = new Thread(this);
        thread.start();

        GameManager.setTransitionEnd(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        gameManager.draw(graphics);
    }

    @Override
    public void run() {
        while (running) {
            checkPaused();
            removeTitle();
            if (!paused) {
                repaint();
                gameManager.run();
            }

            try {
                thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPaused() {
        BitSet bitSet = ((PlayerController) GameManager.playerController).getBitSet();
        if (bitSet.get(KeyEvent.VK_P)) {
            paused();
            Utils.playSound("select.wav", false);
            bitSet.clear();
            pausedPanel.setVisible(true);
        }
    }

    public void addTitle(ImageIcon icon) {
        paused();
        titleExist = 100;
        title.setIcon(icon);
        title.setBounds((GameFrame.WIDTH - icon.getIconWidth()) / 2, (GameFrame.HEIGHT - icon.getIconHeight()) / 2, icon.getIconWidth(), icon.getIconHeight());
        add(title);
    }

    public void removeTitle() {
        if (titleExist > 0) {
            titleExist--;
            if (titleExist == 0) {
                remove(title);
                resume();
                String titleURL = title.getIcon().toString();
                if (titleURL.equals("resources/System/time-up.png"))
                    GameFrame.mainPanel.showEndPanel(EndGamePanel.LOSE, ((PlayerModel) GameManager.playerController.getModel()).getScore());
                if (titleURL.equals("resources/System/win.png")) {
                    if (flag == true) {
                        GameFrame.mainPanel.showStoryEndPanel();
                        flag = false;
                    }
                }
            }
        }
    }

    public void paused() {
        paused = true;
        startPaused = System.currentTimeMillis();
    }

    public void resume() {
        paused = false;
        ((MapManager) GameManager.mapManager).reloadStart(System.currentTimeMillis() - startPaused);
    }

    public static void setFlag(boolean flag) {
        GamePanel.flag = flag;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
