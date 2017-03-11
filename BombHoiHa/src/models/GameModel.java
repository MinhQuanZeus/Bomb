package models;

import controllers.GameController;
import gui.GameFrame;

import java.awt.*;
import java.util.List;

/**
 * Created by QuanT on 3/9/2017.
 */
public class GameModel {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean isAlive;

    public GameModel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isAlive = true;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move(GameVector gameVector, List<GameController> arrBlocks) {
        int dx = this.x + gameVector.dx;
        int dy = this.y + gameVector.dy;

        if ((dx < 0 || dx > GameFrame.WIDTH - width) || (dy < -10 || dy > GameFrame.HEIGHT - 29)) {
            return;
        }

        for (GameController gameController: arrBlocks) {
            if (gameController.getModel().getRect().intersects(getBottomRect(dx, dy))) {
                return;
            }
        }

        this.x = dx;
        this.y = dy;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean overlap(GameModel gameModel) {
        Rectangle rect1 = this.getRect();
        Rectangle rect2 = gameModel.getRect();
        return rect1.intersects(rect2);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBottomRect(int x, int y) {
        return new Rectangle(x + 5, y + height - ItemMapModel.SIZE_TILED / 2, width - 10, ItemMapModel.SIZE_TILED / 2);
    }
}
