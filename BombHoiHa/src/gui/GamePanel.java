package gui;

import manager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private boolean running;
    private GameManager gameManager;

    public GamePanel() {
        setLayout(null);
        setFocusable(true);

        gameManager = new GameManager();
        addKeyListener((KeyListener) gameManager.getPlayerController());

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
            repaint();
            gameManager.run();
            if (!gameManager.getPlayerController().getModel().isAlive()) {
                GameFrame.mainPanel.showPanel(MainPanel.TAG_GAME_OVER);
                running = false;
            }


            try {
                thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
