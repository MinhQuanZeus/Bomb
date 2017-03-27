package models;

import controllers.*;
import gui.GameFrame;
import manager.GameManager;
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
    protected int speed;

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

    public boolean move(GameVector gameVector, List<GameController> arrBlocks) {
        int dx = this.x + gameVector.dx;
        int dy = this.y + gameVector.dy;

        if ((dx < 0 || dx > GameFrame.WIDTH - width) || (dy < -10 || dy > GameFrame.HEIGHT - height - 25)) {
            return false;
        }

        for (int i = 0; i < arrBlocks.size(); i++) {
            GameController gameController = arrBlocks.get(i);
            Rectangle intersectionRect = gameController.getModel().getRect().intersection(getBottomRect(dx, dy));
            int rowBlockMatrix = Utils.getRowMatrix(gameController.getModel().getY());
            int colBlockMatrix = Utils.getColMatrix(gameController.getModel().getX());
            int xRect = dx + 5;
            int yRect = dy + height - ItemMapModel.SIZE_TILED + 5;

            if ((this instanceof PlayerModel) && (gameController instanceof BombController) && !intersectionRect.isEmpty() && !this.overlap(gameController.getModel())) {
                if (((PlayerModel) this).isKick()) {
                    int row = Utils.getRowMatrix(gameController.getModel().getY());
                    int col = Utils.getColMatrix(gameController.getModel().getX());
                    MapManager.map[row][col] = 0;
                    ((BombController) gameController).slide(((PlayerModel) this).getShotDirection());
                }
            }

            if ((gameController instanceof BombController || gameController instanceof BomdEnemyController)&& this.overlap(gameController.getModel())) {
                break;
            } else if (!intersectionRect.isEmpty() && intersectionRect.getHeight() < 25 && intersectionRect.getWidth() < 25) {
                if (gameVector.dy != 0) {
                    if (xRect > gameController.getModel().getX() && MapManager.map[rowBlockMatrix][colBlockMatrix + 1] == 0) {
                        dx += speed;
                    } else if (xRect < gameController.getModel().getX() && MapManager.map[rowBlockMatrix][colBlockMatrix - 1] == 0) {
                        dx -= speed;
                    } else {
                        return false;
                    }
                } else if (gameVector.dx != 0) {
                    if (yRect > gameController.getModel().getY() && MapManager.map[rowBlockMatrix + 1][colBlockMatrix] == 0) {
                        dy += speed;
                    } else if (yRect < gameController.getModel().getY() && MapManager.map[rowBlockMatrix - 1][colBlockMatrix] == 0) {
                        dy -= speed;
                    } else {
                        return false;
                    }
                }
                break;
            } else if (!intersectionRect.isEmpty()) {
                if (this instanceof BombModel) {
                    x = gameController.getModel().getX() - gameVector.dx / speed * ItemMapModel.SIZE_TILED;
                    y = gameController.getModel().getY() - gameVector.dy / speed * ItemMapModel.SIZE_TILED;
                    ((BombModel) this).setSlide(false);
                }
                return false;
            }
        }

        this.x = dx;
        this.y = dy;
        return true;
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

    public Rectangle getIntersectionRect(GameModel other) {
        return getRect().intersection(other.getRect());
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBottomRect(int x, int y) {
        return new Rectangle(x + (width - ItemMapModel.SIZE_TILED + 10) / 2, y + height - ItemMapModel.SIZE_TILED + 10, ItemMapModel.SIZE_TILED - 10, ItemMapModel.SIZE_TILED - 10);
    }

    public int getXRect() {
        return x + (width - ItemMapModel.SIZE_TILED + 10) / 2;
    }

    public int getYRect() {
        return y + height - ItemMapModel.SIZE_TILED + 10;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
