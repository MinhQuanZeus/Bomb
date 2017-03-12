package controllers;

import models.Collision;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import manager.GameManager;
import views.BombView;
import views.GameView;
import views.PlayerView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerController extends GameController implements KeyListener, Collision {

    public static int speed = 2;
    private BitSet bitSet;
    private List<GameController> arrBlocks;

    public PlayerController(PlayerModel model, GameView view, List<GameController> arrBlocks) {
        super(model, view);
        GameManager.controllerManager.add(this);
        GameManager.collisionManager.add(this);
        bitSet = new BitSet(256);
        this.arrBlocks = arrBlocks;
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
        model.move(vector, arrBlocks);
        this.vector.dx = 0;
        this.vector.dy = 0;

        PlayerView view = (PlayerView) this.view;
        if (bitSet.get(KeyEvent.VK_DOWN)) {
            view.setImage(PlayerView.MOVE_DOWN);
            this.vector.dy = speed;
        } else if (bitSet.get(KeyEvent.VK_UP)) {
            view.setImage(PlayerView.MOVE_UP);
            this.vector.dy = -speed;
        } else if (bitSet.get(KeyEvent.VK_LEFT)) {
            view.setImage(PlayerView.MOVE_LEFT);
            this.vector.dx = -speed;
        } else if (bitSet.get(KeyEvent.VK_RIGHT)) {
            view.setImage(PlayerView.MOVE_RIGHT);
            this.vector.dx = speed;
        } else {
            view.setImageHold();
        }

        if (bitSet.get(KeyEvent.VK_SPACE)) {
            bombard();
        }
    }

    private void bombard() {
        PlayerModel model = (PlayerModel) this.model;
        if (model.checkMaxBomb()) {
            int bombX = ((model.getX() + model.getHeight() / 2) / ItemMapModel.SIZE_TILED) * ItemMapModel.SIZE_TILED;
            int bombY = (model.getY() / ItemMapModel.SIZE_TILED + 1) * ItemMapModel.SIZE_TILED;
            new BombController(
                    new GameModel(bombX, bombY, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED),
                    new BombView("Bombs & Explosions/normalbomb"),
                    (PlayerModel) this.getModel()
            );
        }
    }

    @Override
    public void onContact(Collision other) {
        if(other instanceof ItemController){
            if(((ItemController) other).getType()==ItemType.SPEED_UP){
                speed +=1;
            }
        }
    }
}
