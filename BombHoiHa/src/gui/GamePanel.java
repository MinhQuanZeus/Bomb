package gui;

import controllers.GameController;
import manager.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GamePanel extends JPanel implements Runnable {

    public static boolean running;

    private Thread thread;
    private GameManager gameManager;

    public GamePanel() {
        setLayout(null);
        setFocusable(true);

        gameManager = new GameManager();
        addKeyListener((KeyListener) GameManager.playerController);

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

            try {
                thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
