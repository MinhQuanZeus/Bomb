package models;

import controllers.GameController;
import controllers.PlayerController;
import gui.GameFrame;
import manager.MapManager;
import utils.Utils;

import java.awt.*;
import java.util.List;
import java.util.Map;

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

    public void setHeight(int height) {
        this.height = height;
    }

    public void move(GameVector gameVector, List<GameController> arrBlocks) {
        int dx = this.x + gameVector.dx;
        int dy = this.y + gameVector.dy;

        if ((dx < 0 || dx > GameFrame.WIDTH - width) || (dy < -10 || dy > GameFrame.HEIGHT - height - 30)) {
            return;
        }

        for (int i = 0; i < arrBlocks.size(); i++) {
            GameController gameController = arrBlocks.get(i);
            Rectangle intersectionRect = gameController.getModel().getRect().intersection(getBottomRect(dx, dy));

            if (!intersectionRect.isEmpty() && intersectionRect.getHeight() < 20 && intersectionRect.getWidth() < 20) {
                if (gameVector.dy != 0) {
                    if (dx > gameController.getModel().getX()) {
                        dx += intersectionRect.getWidth();
                    } else if (dx < gameController.getModel().getX()) {
                        dx -= intersectionRect.getWidth();
                    }
                } else if (gameVector.dx != 0) {
                    if (dy > gameController.getModel().getY()) {
                        dy += intersectionRect.getWidth();

                    } else if (dy < gameController.getModel().getX()) {
                        dy -= intersectionRect.getWidth();
                    }
                }
            } else if (!intersectionRect.isEmpty()) {
                return;
            }
        }

        this.x = dx;
        this.y = dy;
    }

    public void moveJustUseVector(GameVector gameVector) {
        this.x += gameVector.dx;
        this.y += gameVector.dy;
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
        return new Rectangle(x + 5, y + height - ItemMapModel.SIZE_TILED + 10, width - 10, ItemMapModel.SIZE_TILED - 10);
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
