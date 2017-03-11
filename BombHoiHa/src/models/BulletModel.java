package models;

/**
 * Created by QuanT on 3/12/2017.
 */
public class BulletModel  extends GameModel{
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public BulletModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public BulletModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
    }
}
