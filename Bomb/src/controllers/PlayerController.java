package controllers;

import models.PlayerModel;
import views.GameView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerController extends GameController implements KeyListener {
    public static final int SPEED = 10;
    private KeyInputManager keyInputManage;

    private PlayerController(PlayerModel plane, GameView view) {
        super(plane, view);
        this.keyInputManage = new KeyInputManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.keyInputManage.keyUp = true;
                break;
            case KeyEvent.VK_DOWN:
                this.keyInputManage.keyDown = true;
                break;
            case KeyEvent.VK_LEFT:
                this.keyInputManage.keyLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.keyInputManage.keyRight = true;
                break;
            case KeyEvent.VK_SPACE:
                this.keyInputManage.keySpace = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                this.keyInputManage.keyUp = false;

                break;
            case KeyEvent.VK_DOWN:
                this.keyInputManage.keyDown = false;
                break;
            case KeyEvent.VK_LEFT:
                this.keyInputManage.keyLeft = false;
                break;
            case KeyEvent.VK_RIGHT:
                this.keyInputManage.keyRight = false;
                break;
            case KeyEvent.VK_SPACE:
                this.keyInputManage.keySpace = false;
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);

    }

    @Override
    public void run() {
        super.run();
        this.vector.dx = 0;
        this.vector.dy = 0;

        if (keyInputManage.keyDown && !keyInputManage.keyUp) {
            this.vector.dy = SPEED;
        } else if (!keyInputManage.keyDown && keyInputManage.keyUp) {
            this.vector.dy = -SPEED;
        }

        if (keyInputManage.keyLeft && !keyInputManage.keyRight) {
            this.vector.dx = -SPEED;
        } else if (!keyInputManage.keyLeft && keyInputManage.keyRight) {
            this.vector.dx = SPEED;
        }

    }
}
