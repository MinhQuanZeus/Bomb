package gui;

import controllers.PlayerController;
import manager.GameManager;
import manager.MapManager;
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
    private static boolean flag = true;

    private boolean running;
    private Thread thread;
    private GameManager gameManager;
    private PausedPanel pausedPanel;
    private long startPaused;
    private JLabel title;
    private int titleExist;

    public GamePanel(boolean versus) {
        setLayout(null);
        setFocusable(true);
        gameManager = new GameManager(versus);
        title = new JLabel();
        addKeyListener((KeyListener) GameManager.playerController);
        if (versus) {
            addKeyListener((KeyListener) GameManager.playerTwoController);
            addTitle(new ImageIcon("resources/System/stage-0.png"));
        } else {
            addTitle(new ImageIcon("resources/System/stage-1.png"));
        }
        pausedPanel = new PausedPanel(this);
        add(pausedPanel);

        running = true;
        thread = new Thread(this);
        thread.start();
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
            Utils.playSound("select.wav",false);
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
                    GameFrame.mainPanel.showEndPanel(EndGamePanel.LOSE);
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
