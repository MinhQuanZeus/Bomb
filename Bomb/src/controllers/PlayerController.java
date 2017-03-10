package controllers;

import models.PlayerModel;
import views.Animation;
import views.GameView;
import views.PlayerView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerController extends GameController implements KeyListener {

    public static final int SPEED = 2;
    private BitSet bitSet;

    public PlayerController(PlayerModel model, GameView view) {
        super(model, view);
        bitSet = new BitSet(256);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        bitSet.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        bitSet.clear(e.getKeyCode());
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

        PlayerView view = (PlayerView) this.view;
        if (bitSet.get(KeyEvent.VK_DOWN)) {
            view.setImage(PlayerView.MOVE_DOWN);
            this.vector.dy = SPEED;
        } else if (bitSet.get(KeyEvent.VK_UP)) {
            view.setImage(PlayerView.MOVE_UP);
            this.vector.dy = -SPEED;
        } else if (bitSet.get(KeyEvent.VK_LEFT)) {
            view.setImage(PlayerView.MOVE_LEFT);
            this.vector.dx = -SPEED;
        } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
            view.setImage(PlayerView.MOVE_RIGHT);
            this.vector.dx = SPEED;
        } else {
            view.setImageHold();
        }

    }
}
