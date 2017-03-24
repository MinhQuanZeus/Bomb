package gui;

import controllers.GameController;
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

    private boolean running;
    private Thread thread;
    private GameManager gameManager;
    private PausedPanel pausedPanel;
    private long startPaused;

    public GamePanel() {
        setLayout(null);
        setFocusable(true);
        gameManager = new GameManager();
        addKeyListener((KeyListener) GameManager.playerController);
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
            Utils.playSound("select.wav",false);
            bitSet.clear();
            paused = true;
            pausedPanel.setVisible(true);
            startPaused = System.currentTimeMillis();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    public long getStartPaused() {
        return startPaused;
    }
}
