package utils;

import controllers.PlayerController;
import gui.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by KhoaBeo on 3/25/2017.
 */
public class GameKey implements KeyListener {

    public static GameKey instance = new GameKey();

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            PlayerController.bitSet.clear();
        if (keyCode == KeyEvent.VK_P && GamePanel.paused) {
            return;
        } else {
            PlayerController.bitSet.set(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        PlayerController.bitSet.clear(e.getKeyCode());
    }
}
